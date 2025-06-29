package com.adriantache.photoculling.data

import com.adriantache.photoculling.data.mapper.toData
import com.adriantache.photoculling.data.mapper.toDto
import com.adriantache.photoculling.data.model.ShootsCollectionDto
import com.adriantache.photoculling.data.util.decodeJson
import com.adriantache.photoculling.domain.data.ShootsCollectionDataSource
import com.adriantache.photoculling.domain.data.model.PhotoData
import com.adriantache.photoculling.domain.data.model.ShootData
import com.adriantache.photoculling.domain.data.model.ShootsCollectionData
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val FILE_NAME = "shoots.json"

// TODO: use DI and make this a class instead
object ShootsCollectionDataSourceImpl : ShootsCollectionDataSource {
    private var fileStorage = getShootsFromFile()

    override suspend fun getShoots(): ShootsCollectionData? {
        return fileStorage?.toData()
    }

    // TODO: rethink this, new entity generation should probably be in the domain layer
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addShoot(shoot: ShootData) {
        val fileStorage = fileStorage ?: ShootsCollectionDto(
            id = Uuid.random().toString(),
            shoots = emptyList(),
        )

        this.fileStorage = fileStorage.copy(
            shoots = fileStorage.shoots + shoot.toDto()
        )
        saveShootsToFile()
    }

    override suspend fun deleteShoot(shoot: ShootData) {
        val fileStorage = fileStorage ?: throw IllegalArgumentException("No shoots found!")

        this.fileStorage = fileStorage.copy(
            shoots = fileStorage.shoots - shoot.toDto()
        )
        saveShootsToFile()
    }

    override suspend fun getShoot(shootId: String): ShootData {
        val fileStorage = fileStorage ?: throw IllegalArgumentException("No shoots found!")

        return fileStorage.shoots.find { it.id == shootId }?.toData()
            ?: throw IllegalArgumentException("Cannot find shoot id $shootId in $fileStorage!")
    }

    override suspend fun updatePhoto(
        shootId: String,
        photo: PhotoData
    ) {
        val fileStorage = fileStorage ?: throw IllegalArgumentException("No shoots found!")

        val shoot = fileStorage.shoots.find { it.id == shootId }
            ?: throw IllegalArgumentException("Cannot find shoot id $shootId in $fileStorage!")

        val updatedPhotos = shoot.photos.map {
            if (it.id != photo.id) it

            photo.toDto()
        }

        this.fileStorage = fileStorage.copy(
            shoots = fileStorage.shoots.map {
                if (it.id != shootId) it

                it.copy(photos = updatedPhotos)
            }
        )
    }

    private fun getShootsFromFile(): ShootsCollectionDto? {
        val file = File(FILE_NAME)

        if (!file.exists()) return null

        val json = file.readText()

        return json.decodeJson()
    }

    private fun saveShootsToFile() {
        val file = File(FILE_NAME)
        val json = fileStorage?.let { Json.encodeToString(it) } ?: return

        if (!file.exists()) file.createNewFile()

        file.writeText(json)
    }
}
