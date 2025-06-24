package com.adriantache.photoculling.domain.navigation

import com.adriantache.photoculling.domain.ShootUseCase
import com.adriantache.photoculling.domain.ShootsCollectionUseCase
import com.adriantache.photoculling.domain.navigation.model.NavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

// TODO: DI
object NavigationUseCase {
    val destination: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.ShootsCollectionDestination(ShootsCollectionUseCase.state))

    fun openShoot(shootId: String) {
        destination.update {
            NavigationState.ShootDestination(state = ShootUseCase.state, shootId = shootId)
        }
    }

    fun openPhoto(photoId: String) {
        destination.update {
            NavigationState.PhotoDestination(state = ShootUseCase.state, photoId = photoId)
        }
    }
}
