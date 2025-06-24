package com.adriantache.photoculling.domain.data.mapper

import com.adriantache.photoculling.domain.data.model.ShootData
import com.adriantache.photoculling.domain.entity.Shoot

fun ShootData.toEntity() = Shoot(id = id, name = name, date = date, photos = photos.map { it.toEntity() })

fun Shoot.toData() = ShootData(id = id, name = name, date = date, photos = photos.map { it.toData() })
