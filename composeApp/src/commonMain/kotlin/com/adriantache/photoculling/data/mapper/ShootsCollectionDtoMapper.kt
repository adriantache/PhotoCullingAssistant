package com.adriantache.photoculling.data.mapper

import com.adriantache.photoculling.data.model.ShootsCollectionDto
import com.adriantache.photoculling.domain.data.model.ShootsCollectionData

fun ShootsCollectionData.toDto() = ShootsCollectionDto(id, shoots.map { it.toDto() })
fun ShootsCollectionDto.toData() = ShootsCollectionData(id, shoots.map { it.toData() })
