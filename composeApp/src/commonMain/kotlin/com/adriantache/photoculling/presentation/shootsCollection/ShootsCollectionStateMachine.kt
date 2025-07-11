package com.adriantache.photoculling.presentation.shootsCollection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ShootsCollectionStateMachine(stateFlow: MutableStateFlow<ShootsCollectionState>) {
    val state by stateFlow.collectAsState()

    LaunchedEffect(state) {
        when (val localState = state) {
            is ShootsCollectionState.AddShoot -> Unit
            is ShootsCollectionState.Content -> Unit
            is ShootsCollectionState.Init -> localState.onInit()
        }
    }

    when (val localState = state) {
        is ShootsCollectionState.Init -> Unit
        is ShootsCollectionState.Content -> ShootsCollectionView(localState)
        is ShootsCollectionState.AddShoot -> AddShootView(localState)
    }
}
