package com.example.mycomiclist.screens.addedit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository
import com.example.mycomiclist.domain.repository.SearchRepository
import kotlinx.coroutines.launch

class AddEditViewModel(private val inComic: Comic,
                       private val repository: ComicRepository,
                       private val userId: String,
                       private val searchRepository: SearchRepository,
                       val navigateBack: () -> Unit
) : ViewModel() {
    // Estado del cómic que se está editando o creando
    private val _comic = MutableLiveData<Comic>(inComic)
    val comic: LiveData<Comic> = _comic

    // Estado para pintar los bordes de los TextField en rojo si faltan datos
    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    // Indica si el cómic es nuevo o estamos editando uno existente
    val isNewComic: Boolean = inComic.id.isEmpty()

    fun updateComicState(newComic: Comic) {
        _isError.value = false
        _comic.value = newComic
    }

    // Validación local de campos requeridos
   /* private fun checkComic(): Boolean {
        val currentComic = _comic.value
        if (currentComic == null) {
            _isError.value = true
        } else {
            if (currentComic.title.isEmpty() ||
                currentComic.author.isEmpty() ||
                currentComic.imageUrl.isEmpty() ||
                currentComic.volumeNumber == null) {
                _isError.value = true
            }
        }
        return _isError.value == false
    }*/
    private fun checkComic(): Boolean {
        val comicActual = _comic.value

        if (comicActual == null) {
            println("*** ERROR: El objeto cómic es nulo entero")
            _isError.value = true
        } else {
            // 🕵️‍♂️ Imprimimos el estado de cada campo en la consola de ejecución (Run)
            println("*** Validando título: '${comicActual.title}' -> Vacío: ${comicActual.title.isEmpty()}")
            println("*** Validando autor: '${comicActual.author}' -> Vacío: ${comicActual.author.isEmpty()}")

            // ⚠️ ¡OJO AQUÍ! Revisa cómo se llaman estos campos en TU data class 'Comic'
            // Si tu modelo tiene 'imageUrl' en vez de 'cover', o si 'volumeNumber' es nulo, aquí se bloquea.
            if (comicActual.title.isEmpty()) _isError.value = true
            if (comicActual.author.isEmpty()) _isError.value = true

            // Si tienes más campos obligatorios en el 'if' de tus apuntes (como cover, isbn, etc.)
            // y en el formulario de la pantalla NO los estás rellenando, _isError se vuelve true.
        }

        println("*** RESULTADO FINAL DEL CHEQUEO: ${_isError.value == false}")
        return _isError.value == false
    }

    // Operación: Guardar Nuevo
    fun addComicFirebase() {
        if (checkComic()) {
            viewModelScope.launch {
                repository.addComic(userId, _comic.value!!)
                navigateBack() // Volvemos a la lista automáticamente
            }
        }
    }

    // Operación: Actualizar Existente
    fun updateComicFirebase() {
        if (checkComic()) {
            viewModelScope.launch {
                repository.updateComic(userId, _comic.value!!)
                navigateBack()
            }
        }
    }

    // Operación: Eliminar Documento
    fun deleteComicFirebase() {
        viewModelScope.launch {
            repository.deleteComic(userId, _comic.value!!.id)
            navigateBack()
        }
    }
//Openlibrary
// 🌟 CONTROL DE ESTADOS DE LA API (Págs 54 y 65)
var apiUIState: ApiComicUIState by mutableStateOf(ApiComicUIState.Idle)
    private set

    sealed interface ApiComicUIState {
        data class Success(val comic: Comic) : ApiComicUIState
        object Error : ApiComicUIState
        object Loading : ApiComicUIState
        object Idle : ApiComicUIState
    }

    // 🌟 FUNCIÓN QUE LLAMA A LA API EN UN HILO SECUNDARIO
    fun searchComicByIsbn() {
        val isbnActual = _comic.value?.title ?: "" // Asegúrate de vincular tu campo de texto del ISBN

        apiUIState = ApiComicUIState.Loading // 1. Cambiamos a cargando

        viewModelScope.launch {
            try {
                // 2. Buscamos en la API de Open Library pasándole el texto del ISBN
                val resultComic = searchRepository.getBookInfoByIsbn(isbnActual)
                apiUIState = ApiComicUIState.Success(resultComic)

                // 3. Rellenamos el formulario automáticamente manteniendo los datos estructurales
                _comic.value = _comic.value?.copy(
                    title = resultComic.title,
                    author = resultComic.author,
                    imageUrl = resultComic.imageUrl
                )
            } catch (e: Exception) {
                apiUIState = ApiComicUIState.Error // 4. Capturamos fallos de red
                Log.e("AddEditViewModel", "Error en la API: ${e.message}")
            }
        }
    }
}