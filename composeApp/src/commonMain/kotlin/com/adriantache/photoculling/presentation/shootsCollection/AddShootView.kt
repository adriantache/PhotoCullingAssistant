package com.adriantache.photoculling.presentation.shootsCollection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import com.adriantache.photoculling.platform.getPhotoPickerResults
import com.adriantache.photoculling.platform.pickPhotos
import com.adriantache.photoculling.presentation.util.Spacer

@Composable
fun AddShootView(localState: ShootsCollectionState.AddShoot) {
    val addedPhotos by getPhotoPickerResults().collectAsState(null)
    var showAddShootDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showAddShootDialog, addedPhotos) {
        if (showAddShootDialog && addedPhotos == null) pickPhotos()
    }

    LaunchedEffect(addedPhotos) {
        addedPhotos?.let { addedPhotos ->
            localState.onSetPhotos(addedPhotos)
        }

        showAddShootDialog = false
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = localState.shoot.name,
            onValueChange = localState.onSetName,
        )

        Spacer(16.dp)

        Button(onClick = { showAddShootDialog = true }) {
            Text("Add Photos")
        }

        Spacer(16.dp)

        addedPhotos?.let { addedPhotos ->
            Text("Added Photos: ${addedPhotos.size}")

            Spacer(16.dp)

            Button(onClick = { localState.onSubmit() }) {
                Text("Submit")
            }
        }
    }
}
