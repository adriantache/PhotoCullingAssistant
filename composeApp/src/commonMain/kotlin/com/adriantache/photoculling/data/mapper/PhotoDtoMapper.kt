package com.adriantache.photoculling.data.mapper

import com.adriantache.photoculling.data.model.PhotoDto
import com.adriantache.photoculling.domain.data.model.PhotoData

fun PhotoData.toDto() = PhotoDto(id, uri, rating, isSeen)
fun PhotoDto.toData() = PhotoData(id, uri, rating, isSeen)
