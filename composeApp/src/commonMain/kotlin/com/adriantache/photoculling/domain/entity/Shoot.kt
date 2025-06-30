package com.adriantache.photoculling.domain.entity

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
data class Shoot(
    val id: String = Uuid.random().toString(),
    val name: String = "",
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val photos: List<Photo> = emptyList(),
) {
    val progress = photos.count { it.isSeen }

    fun changeName(name: String): Shoot = this.copy(name = name)

    fun deletePhoto(photo: Photo): Shoot = this.copy(
        photos = photos - photo
    )

    fun addPhotos(newPhotos: List<Photo>): Shoot = this.copy(photos = photos + newPhotos)
}
