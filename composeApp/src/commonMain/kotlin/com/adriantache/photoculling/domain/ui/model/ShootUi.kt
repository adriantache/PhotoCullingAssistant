package com.adriantache.photoculling.domain.ui.model

data class ShootUi(
    val id: String,
    val name: String,
    val date: Long,
    val photos: List<PhotoUi>,
    val hasPhotos: Boolean,
    val selectedPhotoId: String?,
    val selectedPhoto: PhotoUi?,
    val progress: Int,
)
