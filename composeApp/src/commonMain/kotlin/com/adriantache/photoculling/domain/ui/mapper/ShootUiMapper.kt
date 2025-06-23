package com.adriantache.photoculling.domain.ui.mapper

import com.adriantache.photoculling.domain.entity.Shoot
import com.adriantache.photoculling.domain.ui.model.ShootUi

fun ShootUi.toEntity() = Shoot(name, date, photos.map { it.toEntity() })
fun Shoot.toUi() = ShootUi(name, date, photos.map { it.toUi() })
