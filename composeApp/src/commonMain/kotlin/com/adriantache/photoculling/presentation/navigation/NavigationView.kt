package com.adriantache.photoculling.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.adriantache.photoculling.domain.navigation.NavigationUseCase
import com.adriantache.photoculling.domain.navigation.model.NavigationState.ShootDestination
import com.adriantache.photoculling.domain.navigation.model.NavigationState.ShootsCollectionDestination
import com.adriantache.photoculling.platform.BackHandler
import com.adriantache.photoculling.presentation.shoot.ShootStateMachine
import com.adriantache.photoculling.presentation.shootsCollection.ShootsCollectionStateMachine

// TODO: hook navigation into back gestures
@Composable
fun NavigationView() {
    val navigationState by NavigationUseCase.state.collectAsState()

    BackHandler(enabled = navigationState !is ShootsCollectionDestination) {
        navigationState.onBack()
    }

    when (val state = navigationState) {
        is ShootDestination -> ShootStateMachine(state.state, state.shootId)
        is ShootsCollectionDestination -> ShootsCollectionStateMachine(stateFlow = state.state)
    }
}
