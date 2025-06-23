package com.adriantache.photoculling.domain.ui.mapper

import com.adriantache.photoculling.domain.entity.Photo
import com.adriantache.photoculling.domain.ui.model.PhotoUi

fun PhotoUi.toEntity() = Photo(uri, rating)

fun Photo.toUi() = PhotoUi(uri, rating)
