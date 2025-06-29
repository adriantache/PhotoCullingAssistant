package com.adriantache.photoculling.presentation.shoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.adriantache.photoculling.domain.state.ShootState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ShootStateMachine(stateFlow: MutableStateFlow<ShootState>, shootId: String) {
    val state by stateFlow.collectAsState()

    LaunchedEffect(state) {
        when (val localState = state) {
            is ShootState.Content -> Unit
            is ShootState.Init -> localState.onInit(shootId)
        }
    }

    when (val localState = state) {
        is ShootState.Content -> ShootView(localState)
        is ShootState.Init -> Unit
    }
}
