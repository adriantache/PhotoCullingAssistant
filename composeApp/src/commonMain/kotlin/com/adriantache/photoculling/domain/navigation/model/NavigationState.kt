package com.adriantache.photoculling.domain.navigation.model

import com.adriantache.photoculling.domain.state.ShootState
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import kotlinx.coroutines.flow.MutableStateFlow

sealed class NavigationState(open val onBack: () -> Unit) {
    data class ShootsCollectionDestination(
        val state: MutableStateFlow<ShootsCollectionState>,
        override val onBack: () -> Unit,
    ) : NavigationState(onBack)

    data class ShootDestination(
        val state: MutableStateFlow<ShootState>,
        val shootId: String,
        override val onBack: () -> Unit,
    ) : NavigationState(onBack)
}
