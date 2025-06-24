package com.adriantache.photoculling.domain.data.mapper

import com.adriantache.photoculling.domain.data.model.PhotoData
import com.adriantache.photoculling.domain.entity.Photo

fun PhotoData.toEntity() = Photo(id = id, uri = uri, rating = rating, isSeen = isSeen)

fun Photo.toData() = PhotoData(id = id, uri = uri, rating = rating, isSeen = isSeen)
