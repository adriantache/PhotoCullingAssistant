package com.adriantache.photoculling.domain.navigation

import com.adriantache.photoculling.domain.ShootUseCase
import com.adriantache.photoculling.domain.ShootsCollectionUseCase
import com.adriantache.photoculling.domain.navigation.model.NavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

// TODO: DI
object NavigationUseCase {
    val state: MutableStateFlow<NavigationState> = MutableStateFlow(
        NavigationState.ShootsCollectionDestination(
            state = ShootsCollectionUseCase.state,
            onBack = {}, // Root destination.
        )
    )

    fun openShoot(shootId: String) {
        state.update {
            NavigationState.ShootDestination(
                state = ShootUseCase.state,
                shootId = shootId,
                onBack = {
                    state.update {
                        NavigationState.ShootsCollectionDestination(
                            state = ShootsCollectionUseCase.state,
                            onBack = {}, // Root destination.
                        )
                    }
                }
            )
        }
    }
}
