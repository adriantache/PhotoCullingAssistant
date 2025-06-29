package com.adriantache.photoculling.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val id: String,
    val uri: String,
    val rating: Int?,
    val isSeen: Boolean,
)
