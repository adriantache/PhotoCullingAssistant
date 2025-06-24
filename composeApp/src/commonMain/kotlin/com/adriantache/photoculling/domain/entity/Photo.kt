package com.adriantache.photoculling.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Photo @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val uri: String,
    val rating: Int,
    val isSeen: Boolean = false,
) {
    val isValid = rating in 0..6

    val isPicked = rating > 5
    val isRejected = rating < 1

    fun setRating(rating: Int) = this.copy(rating = rating)

    fun setSeen() = this.copy(isSeen = true)
}
