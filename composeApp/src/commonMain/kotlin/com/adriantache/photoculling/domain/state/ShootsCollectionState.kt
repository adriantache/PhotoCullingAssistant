package com.adriantache.photoculling.domain.state

import com.adriantache.photoculling.domain.ui.model.PhotoUi
import com.adriantache.photoculling.domain.ui.model.ShootUi
import com.adriantache.photoculling.domain.ui.model.ShootsCollectionUi

sealed interface ShootsCollectionState {
    data class Init(val onInit: () -> Unit) : ShootsCollectionState

    data class Content(
        val content: ShootsCollectionUi,
        val onAddShoot: () -> Unit,
//        val onDeleteShoot: () -> Unit, TODO
//        val onEditShoot: () -> Unit, TODO
    ) : ShootsCollectionState

    data class AddShoot(
        val shoot: ShootUi?,
        val onSetName: (String) -> Unit,
        val onSetPhotos: (List<PhotoUi>) -> Unit,
        val onSubmit: () -> Unit,
    ) : ShootsCollectionState
}
