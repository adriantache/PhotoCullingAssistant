package com.adriantache.photoculling.domain.data

import com.adriantache.photoculling.domain.data.model.PhotoData
import com.adriantache.photoculling.domain.data.model.ShootData
import com.adriantache.photoculling.domain.data.model.ShootsCollectionData

// TODO: error handling
interface ShootsCollectionDataSource {
    suspend fun getShoots(): ShootsCollectionData?
    suspend fun addShoot(shoot: ShootData)
    suspend fun deleteShoot(shoot: ShootData)
    suspend fun getShoot(shootId: String): ShootData
    suspend fun updatePhoto(shootId: String, photo: PhotoData)
}
