package com.adriantache.photoculling.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShootDto(
    val id: String,
    val name: String,
    val date: Long,
    val photos: List<PhotoDto>,
)
