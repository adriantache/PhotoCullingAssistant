package com.adriantache.photoculling.domain.data.mapper

import com.adriantache.photoculling.domain.data.model.ShootData
import com.adriantache.photoculling.domain.entity.Shoot

fun ShootData.toEntity() = Shoot(name, date, photos.map { it.toEntity() })

fun Shoot.toData() = ShootData(name, date, photos.map { it.toData() })
