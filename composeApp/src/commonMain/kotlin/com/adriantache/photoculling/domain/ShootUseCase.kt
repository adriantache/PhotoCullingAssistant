package com.adriantache.photoculling.domain

import com.adriantache.photoculling.data.ShootsCollectionDataSourceImpl
import com.adriantache.photoculling.domain.data.ShootsCollectionDataSource
import com.adriantache.photoculling.domain.data.mapper.toEntity
import com.adriantache.photoculling.domain.entity.Shoot
import com.adriantache.photoculling.domain.navigation.NavigationUseCase
import com.adriantache.photoculling.domain.state.ShootState
import com.adriantache.photoculling.domain.ui.mapper.toUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO: DI
object ShootUseCase {
    private val data: ShootsCollectionDataSource = ShootsCollectionDataSourceImpl
    private val navigation: NavigationUseCase = NavigationUseCase

    private val scope = CoroutineScope(Dispatchers.IO)

    val state: MutableStateFlow<ShootState> = MutableStateFlow(ShootState.Init(::onInit))

    private var shoot: Shoot? = null
    private var selectedPhotoId: String? = null

    private fun onInit(shootId: String) {
        refreshStoredShoot(shootId)
    }

    private fun refreshStoredShoot(shootId: String) {
        loadShoot(shootId)
    }

    private fun loadShoot(shootId: String) {
        scope.launch {
            shoot = data.getShoot(shootId).toEntity()

            shoot?.let { shoot ->
                selectedPhotoId = shoot.photos.firstOrNull { !it.isSeen }?.id ?: shoot.photos.first().id
            }

            updateState()
        }
    }

    private fun updateState() {
        val shoot = shoot ?: return

        state.update {
            ShootState.Content(
                shoot = shoot.toUi(selectedPhotoId),
                onClickPhoto = {// TODO: probably remove this
                    selectedPhotoId = it
                    navigation.openPhoto(it)
                },
                onNavigateToNextPhoto = { isForward ->
                    val currentSelectedPhotoIndex = shoot.photos.indexOfFirst { it.id == selectedPhotoId }
                    // TODO: add end condition
                    val nextIndex = if (isForward && currentSelectedPhotoIndex < (shoot.photos.size - 1)) {
                        currentSelectedPhotoIndex + 1
                    } else if (currentSelectedPhotoIndex > 0) {
                        currentSelectedPhotoIndex - 1
                    } else {
                        currentSelectedPhotoIndex // TODO: rethink this case
                    }

                    selectedPhotoId = shoot.photos[nextIndex].id
                    updateState()
                }
            )
        }
    }
}
