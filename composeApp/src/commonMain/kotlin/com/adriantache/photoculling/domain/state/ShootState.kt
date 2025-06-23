package com.adriantache.photoculling.domain.state

import com.adriantache.photoculling.domain.ui.model.ShootUi

sealed interface ShootState {
    data class Init(val onInit: () -> Unit) : ShootState

    data class Content(val content: ShootUi) : ShootState
}
