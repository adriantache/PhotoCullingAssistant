package com.adriantache.photoculling.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ShootsCollection @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val shoots: List<Shoot>
) {
    fun addShoot(shoot: Shoot) = this.copy(shoots = listOf(shoot) + shoots)
}
