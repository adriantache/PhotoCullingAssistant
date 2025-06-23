package com.adriantache.photoculling.domain.entity

data class ShootsCollection(
    val shoots: List<Shoot>
) {
    fun addShoot(shoot: Shoot) = this.copy(shoots = listOf(shoot) + shoots)
}
