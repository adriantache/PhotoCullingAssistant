package com.adriantache.photoculling.domain.state

import com.adriantache.photoculling.domain.ui.model.ShootUi

sealed interface ShootState {
    data class Init(val onInit: (shootId: String) -> Unit) : ShootState

    data class Content(
        val shoot: ShootUi,
        val onClickPhoto: (photoId: String) -> Unit, // TODO: consider removing this?
        val onNavigateToNextPhoto: (isForward: Boolean) -> Unit,
        val onSetRating: (rating: Int) -> Unit,
    ) : ShootState
}
