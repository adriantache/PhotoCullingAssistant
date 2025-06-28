package com.adriantache.photoculling.data

import com.adriantache.photoculling.domain.data.ShootsCollectionDataSource
import com.adriantache.photoculling.domain.data.model.ShootData
import com.adriantache.photoculling.domain.data.model.ShootsCollectionData

// TODO: implement
class ShootsCollectionDataSourceImpl : ShootsCollectionDataSource {
    override suspend fun getShoots(): ShootsCollectionData? {
        return null
    }

    override suspend fun addShoot(shoot: ShootData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShoot(shoot: ShootData) {
        TODO("Not yet implemented")
    }
}
