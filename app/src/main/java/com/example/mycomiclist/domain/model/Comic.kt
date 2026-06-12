package com.example.mycomiclist.domain.model

import com.google.firebase.firestore.PropertyName

data class Comic(
    val id: String ="",
    val title: String = "",
    val author: String= "",
    val volumeNumber: Int= 1,
    val imageUrl: String= "",

    // Solución al fallo de isRead / read de firestone
    @get:PropertyName("read")
    @set:PropertyName("read")
    var isRead: Boolean = false
    //val isRead: Boolean = false
)