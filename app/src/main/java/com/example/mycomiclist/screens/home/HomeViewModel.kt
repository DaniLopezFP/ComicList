package com.example.mycomiclist.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: ComicRepository,
    val userName: String,
    private val userId: String,
    val goToAddEditScreen: (Comic) -> Unit,
    val goBack: () -> Unit
) : ViewModel() {

    // Índice de navegación de la barra inferior (0: Todos, 1: Leídos, etc.)
    private val _contentIndex = MutableStateFlow(0)
    val contentIndex: StateFlow<Int> = _contentIndex

    // 🌟 COMBINACIÓN DE FLUJOS: Cómics en tiempo real filtrados por pestaña
    val comicList: StateFlow<List<Comic>> = repository.getAllComics(userId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun changeIndex(newIndex: Int) {
        _contentIndex.value = newIndex
    }

    // Filtra la lista según la pestaña seleccionada de la barra
    private fun filterComicList(comics: List<Comic>, index: Int): List<Comic> {
        return when (index) {
            1 -> comics.filter { it.isRead }       // Solo leídos
            2 -> comics.filter { !it.isRead }      // Solo pendientes
            else -> comics                         // Todos
        }
    }
}