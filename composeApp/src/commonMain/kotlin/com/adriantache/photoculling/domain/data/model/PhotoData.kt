package com.adriantache.photoculling.domain.data.model

data class PhotoData(
    val id: String,
    val uri: String,
    val rating: Int?,
    val isSeen: Boolean,
)
