package com.adriantache.photoculling.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShootsCollectionDto(
    val id: String,
    val shoots: List<ShootDto>,
)
