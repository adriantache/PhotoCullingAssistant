package com.adriantache.photoculling.domain.ui.mapper

import com.adriantache.photoculling.domain.entity.Shoot
import com.adriantache.photoculling.domain.ui.model.ShootUi

fun ShootUi.toEntity() = Shoot(
    id = id,
    name = name,
    date = date,
    photos = photos.map { it.toEntity() },
)

fun Shoot.toUi(selectedPhotoId: String?) = ShootUi(
    id = id,
    name = name,
    date = date,
    photos = photos.map { it.toUi() },
    progress = progress,
    selectedPhotoId = selectedPhotoId
)
