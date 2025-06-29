package com.adriantache.photoculling.data.mapper

import com.adriantache.photoculling.data.model.ShootDto
import com.adriantache.photoculling.domain.data.model.ShootData

fun ShootData.toDto() = ShootDto(id, name, date, photos.map { it.toDto() })
fun ShootDto.toData() = ShootData(id, name, date, photos.map { it.toData() })
