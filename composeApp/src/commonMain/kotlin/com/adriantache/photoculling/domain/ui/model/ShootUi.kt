package com.adriantache.photoculling.domain.ui.model

data class ShootUi(
    val name: String,
    val date: Long,
    val photos: List<PhotoUi>,
)
