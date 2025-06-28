package com.adriantache.photoculling.domain

import com.adriantache.photoculling.domain.data.ShootDataSource
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
    private val data: ShootDataSource = TODO("ShootDataSourceImpl")
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
        updateState()
    }

    private fun loadShoot(shootId: String) {
        scope.launch {
            shoot = data.getShoot(shootId).toEntity()
        }
    }

    private fun updateState() {
        val shoot = shoot ?: return

        state.update {
            ShootState.Content(
                shoot = shoot.toUi(selectedPhotoId),
                onClickPhoto = {
                    selectedPhotoId = it
                    navigation.openPhoto(it)
                }
            )
        }
    }
}
