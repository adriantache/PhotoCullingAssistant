package com.adriantache.photoculling.domain

import com.adriantache.photoculling.domain.data.ShootsCollectionDataSource
import com.adriantache.photoculling.domain.data.mapper.toData
import com.adriantache.photoculling.domain.data.mapper.toEntity
import com.adriantache.photoculling.domain.entity.Shoot
import com.adriantache.photoculling.domain.entity.ShootsCollection
import com.adriantache.photoculling.domain.navigation.NavigationUseCase
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import com.adriantache.photoculling.domain.ui.mapper.toEntity
import com.adriantache.photoculling.domain.ui.mapper.toUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO: DI
object ShootsCollectionUseCase {
    private val data: ShootsCollectionDataSource = TODO("ShootsCollectionDataSourceImpl")
    private val navigation = NavigationUseCase

    private val scope = CoroutineScope(Dispatchers.IO)

    val state: MutableStateFlow<ShootsCollectionState> = MutableStateFlow(ShootsCollectionState.Init(::onInit))

    private var shootsCollection: ShootsCollection? = null
    private var newShoot: Shoot? = null

    private fun onInit() {
        refreshStoredShoots()
    }

    private fun refreshStoredShoots() {
        loadExistingShoots()
        updateState()
    }

    private fun loadExistingShoots() {
        scope.launch {
            shootsCollection = data.getShoots()?.toEntity() ?: ShootsCollection(shoots = emptyList())
        }
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
                )
            }
        }
    }

    private fun updateNewShootState() {
        newShoot?.let { shoot ->
            state.update {
                ShootsCollectionState.AddShoot(
                    shoot = shoot.toUi(),
                    onSetName = {
                        newShoot = newShoot?.changeName(it)
                        updateNewShootState()
                    },
                    onSetPhotos = { newPhotos ->
                        newShoot = newShoot?.addPhotos(newPhotos.map { it.toEntity() })
                        updateNewShootState()
                    },
                    onSubmit = {
                        scope.launch {
                            data.addShoot(shoot.toData())
                            newShoot = null
                            refreshStoredShoots()
                        }
                    }
                )
            }
        }
    }
}
