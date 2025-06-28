package com.adriantache.photoculling.domain.navigation

import com.adriantache.photoculling.domain.ShootUseCase
import com.adriantache.photoculling.domain.ShootsCollectionUseCase
import com.adriantache.photoculling.domain.navigation.model.NavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

// TODO: DI
object NavigationUseCase {
    val state: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.ShootsCollectionDestination(ShootsCollectionUseCase.state))

    fun openShoot(shootId: String) {
        state.update {
            NavigationState.ShootDestination(state = ShootUseCase.state, shootId = shootId)
        }
    }

    fun openPhoto(photoId: String) {
        state.update {
            NavigationState.PhotoDestination(state = ShootUseCase.state, photoId = photoId)
        }
    }
}
