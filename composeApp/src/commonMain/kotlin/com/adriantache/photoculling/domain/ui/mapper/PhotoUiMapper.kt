package com.adriantache.photoculling.domain.ui.mapper

import com.adriantache.photoculling.domain.entity.Photo
import com.adriantache.photoculling.domain.ui.model.PhotoUi

fun PhotoUi.toEntity() = Photo(id = id, uri = uri, rating = rating, isSeen = isSeen)

fun Photo.toUi() = PhotoUi(id = id, uri = uri, rating = rating, isSeen = isSeen)
