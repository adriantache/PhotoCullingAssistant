package com.adriantache.photoculling.domain.entity

data class Photo(
    val uri: String,
    val rating: Int,
) {
    val isValid = rating in 0..6

    val isPicked = rating > 5
    val isRejected = rating < 1

    fun setRating(rating: Int) = this.copy(rating = rating)
}
