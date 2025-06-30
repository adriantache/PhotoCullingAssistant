package com.adriantache.photoculling.domain

import com.adriantache.photoculling.data.ShootsCollectionDataSourceImpl
import com.adriantache.photoculling.domain.data.ShootsCollectionDataSource
import com.adriantache.photoculling.domain.data.mapper.toData
import com.adriantache.photoculling.domain.data.mapper.toEntity
import com.adriantache.photoculling.domain.entity.Photo
import com.adriantache.photoculling.domain.entity.Shoot
import com.adriantache.photoculling.domain.entity.ShootsCollection
import com.adriantache.photoculling.domain.navigation.NavigationUseCase
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import com.adriantache.photoculling.domain.ui.mapper.toUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO: DI
object ShootsCollectionUseCase {
    private val data: ShootsCollectionDataSource = ShootsCollectionDataSourceImpl
    private val navigation = NavigationUseCase

    private val scope = CoroutineScope(Dispatchers.IO)

    val state: MutableStateFlow<ShootsCollectionState> = MutableStateFlow(ShootsCollectionState.Init(::onInit))

    private var shootsCollection: ShootsCollection? = null
    private var newShoot: Shoot? = null

    private fun onInit() {
        refreshStoredShoots()
    }

    private fun refreshStoredShoots() {
        scope.launch {
            loadExistingShoots()
            updateState()
        }

    }

    private suspend fun loadExistingShoots() {
        shootsCollection = data.getShoots()?.toEntity() ?: ShootsCollection(shoots = emptyList())
    }

    private fun onAddShoot() {
        newShoot = Shoot()

        updateNewShootState()
    }

    private fun updateState() {
        shootsCollection?.let { shootsCollection ->
            state.update {
                ShootsCollectionState.Content(
                    shoots = shootsCollection.toUi(),
                    onAddShoot = ::onAddShoot,
                    onOpenShoot = { navigation.openShoot(it) },
                    onDeleteShoot = { id ->
                        scope.launch {
                            data.deleteShoot(shootsCollection.shoots.first { it.id == id }.toData())
                            refreshStoredShoots()
                        }
                    }
                )
            }
        }
    }

    private fun updateNewShootState() {
        newShoot?.let { shoot ->
            state.update {
                ShootsCollectionState.AddShoot(
                    shoot = shoot.toUi(selectedPhotoId = null),
                    onSetName = {
                        newShoot = newShoot?.changeName(it)
                        updateNewShootState()
                    },
                    onSetPhotos = { newPhotos ->
                        newShoot = newShoot?.addPhotos(newPhotos.map { Photo(uri = it) })
                        updateNewShootState()
                    },
                    onSubmit = {
                        scope.launch {
                            data.addShoot(shoot.toData())
                            newShoot = null
                            refreshStoredShoots()
                        }
                    },
                    onDismiss = {
                        updateState()
                    }
                )
            }
        }
    }
}
