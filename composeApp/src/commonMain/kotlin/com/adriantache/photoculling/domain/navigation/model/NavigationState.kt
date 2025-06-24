package com.adriantache.photoculling.domain.navigation.model

import com.adriantache.photoculling.domain.state.ShootState
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import kotlinx.coroutines.flow.MutableStateFlow

sealed interface NavigationState {
    data class ShootsCollectionDestination(val state: MutableStateFlow<ShootsCollectionState>) : NavigationState
    data class ShootDestination(val state: MutableStateFlow<ShootState>, val shootId: String) : NavigationState
    data class PhotoDestination(val state: MutableStateFlow<ShootState>, val photoId: String) : NavigationState
}
