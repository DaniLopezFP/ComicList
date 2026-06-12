package com.example.mycomiclist.domain.model

data class Comic(
    val id: String ="",
    val title: String = "",
    val author: String= "",
    val volumeNumber: Int= 1,
    val imageUrl: String= "",
    val isRead: Boolean = false
)