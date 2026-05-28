package com.example.mycomiclist.domain.model

data class Comic(
    val id: String,
    val title: String,
    val author: String,
    val volumeNumber: Int,
    val imageUrl: String,
    val isRead: Boolean = false
)