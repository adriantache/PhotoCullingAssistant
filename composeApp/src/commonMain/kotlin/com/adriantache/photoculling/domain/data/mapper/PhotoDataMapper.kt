package com.adriantache.photoculling.domain.data.mapper

import com.adriantache.photoculling.domain.data.model.PhotoData
import com.adriantache.photoculling.domain.entity.Photo

fun PhotoData.toEntity() = Photo(uri, rating)

fun Photo.toData() = PhotoData(uri, rating)
