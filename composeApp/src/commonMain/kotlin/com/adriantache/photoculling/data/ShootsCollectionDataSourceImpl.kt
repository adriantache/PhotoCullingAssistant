package com.adriantache.photoculling.data

import com.adriantache.photoculling.data.mapper.toData
import com.adriantache.photoculling.data.mapper.toDto
import com.adriantache.photoculling.data.model.ShootsCollectionDto
import com.adriantache.photoculling.data.util.decodeJson
import com.adriantache.photoculling.domain.data.ShootsCollectionDataSource
import com.adriantache.photoculling.domain.data.model.PhotoData
import com.adriantache.photoculling.domain.data.model.ShootData
import com.adriantache.photoculling.domain.data.model.ShootsCollectionData
import com.adriantache.photoculling.platform.getFilePath
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val FILE_NAME = "shoots.json"

// TODO: use DI and make this a class instead
object ShootsCollectionDataSourceImpl : ShootsCollectionDataSource {
    private var fileStorage = getShootsFromFile()
        set(value) {
            field = value
            saveShootsToFile()
        }

    override suspend fun getShoots(): ShootsCollectionData? {
        return fileStorage?.toData()
    }

    // TODO: rethink this, new entity generation should probably be in the domain layer
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addShoot(shoot: ShootData) {
        val fileStorage = fileStorage
            ?: ShootsCollectionDto(
                id = Uuid.random().toString(),
                shoots = emptyList(),
            )

        val fixedShoot = shoot.copy(
            photos = shoot.photos.map { it.copy(uri = it.uri.fixUri()) }
        )

        this.fileStorage = fileStorage.copy(
            shoots = fileStorage.shoots + fixedShoot.toDto()
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
            if (it.id != photo.id) return@map it

            photo.toDto()
        }

        this.fileStorage = fileStorage.copy(
            shoots = fileStorage.shoots.map {
                if (it.id != shootId) return@map it

                it.copy(photos = updatedPhotos)
            }
        )
    }

    private fun getShootsFromFile(): ShootsCollectionDto? {
        val file = getFile()

        if (!file.exists()) return null

        val json = file.readText()

        return json.decodeJson()
    }

    private fun saveShootsToFile() {
        val file = getFile()
        val json = fileStorage?.let { Json.encodeToString(it) } ?: return

        if (!file.exists()) file.createNewFile()

        file.writeText(json)
    }

    private fun getFile(): File = File(getFilePath(FILE_NAME))

    private fun String.fixUri(): String {
        return this.replace("file:/", "file:///")
    }
}
