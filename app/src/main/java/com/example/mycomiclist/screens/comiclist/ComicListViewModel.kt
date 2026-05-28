package com.example.mycomiclist.screens.comiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mycomiclist.data.repository.ComicRepositoryImpl
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository

class ComicListViewModel : ViewModel() {

    // Instanciamos el repositorio (en el futuro se usaría inyección de dependencias)
    private val repository: ComicRepository = ComicRepositoryImpl()

    // Exponemos el LiveData de cómics del repositorio a la vista
    val comics: LiveData<List<Comic>> = repository.getAllComics()

    // Lógica para cambiar el estado de lectura de un cómic
    fun toggleReadStatus(comicId: String) {
        repository.toggleComicReadStatus(comicId)
    }
}