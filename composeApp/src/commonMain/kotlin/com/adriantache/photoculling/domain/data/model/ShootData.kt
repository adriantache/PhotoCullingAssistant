package com.adriantache.photoculling.domain.data.model

data class ShootData(
    val id: String,
    val name: String,
    val date: Long,
    val photos: List<PhotoData>,
)
