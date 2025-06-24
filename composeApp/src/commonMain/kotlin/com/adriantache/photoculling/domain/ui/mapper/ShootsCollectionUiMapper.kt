package com.adriantache.photoculling.domain.ui.mapper

import com.adriantache.photoculling.domain.entity.ShootsCollection
import com.adriantache.photoculling.domain.ui.model.ShootsCollectionUi

fun ShootsCollectionUi.toEntity() = ShootsCollection(
    id = id,
    shoots = shoots.map { it.toEntity() }
)

fun ShootsCollection.toUi(selectedPhotoId: String) = ShootsCollectionUi(
    id = id,
    shoots = shoots.map { it.toUi(selectedPhotoId) }
)
