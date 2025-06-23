package com.adriantache.photoculling.domain.entity

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Shoot @OptIn(ExperimentalTime::class) constructor(
    val name: String = "",
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val photos: List<Photo> = emptyList(),
) {
    fun changeName(name: String): Shoot = this.copy(name = name)

    fun deletePhoto(photo: Photo): Shoot = this.copy(
        photos = photos - photo
    )

    fun addPhotos(newPhotos: List<Photo>): Shoot = this.copy(photos = photos + newPhotos)
}
