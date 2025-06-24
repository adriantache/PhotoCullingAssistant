package com.adriantache.photoculling.domain.data.mapper

import com.adriantache.photoculling.domain.data.model.ShootsCollectionData
import com.adriantache.photoculling.domain.entity.ShootsCollection

fun ShootsCollectionData.toEntity() = ShootsCollection(id = id, shoots = shoots.map { it.toEntity() })

fun ShootsCollection.toData() = ShootsCollectionData(id = id, shoots = shoots.map { it.toData() })
