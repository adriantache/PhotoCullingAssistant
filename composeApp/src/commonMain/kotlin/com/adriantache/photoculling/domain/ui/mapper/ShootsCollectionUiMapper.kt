package com.adriantache.photoculling.domain.ui.mapper

import com.adriantache.photoculling.domain.entity.ShootsCollection
import com.adriantache.photoculling.domain.ui.model.ShootsCollectionUi

fun ShootsCollectionUi.toEntity() = ShootsCollection(shoots.map { it.toEntity() })
fun ShootsCollection.toUi() = ShootsCollectionUi(shoots.map { it.toUi() })
