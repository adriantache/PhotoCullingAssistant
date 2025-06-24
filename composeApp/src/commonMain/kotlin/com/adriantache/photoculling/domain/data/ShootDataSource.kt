package com.adriantache.photoculling.domain.data

import com.adriantache.photoculling.domain.data.model.PhotoData
import com.adriantache.photoculling.domain.data.model.ShootData

// TODO: error handling
interface ShootDataSource {
    fun getShoot(shootId: String): ShootData
    fun updatePhoto(shootId: String, photo: PhotoData)
}
