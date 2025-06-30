package com.adriantache.photoculling.presentation.shootsCollection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

    Dialog(onDismissRequest = { localState.onDismiss() }) {
        Column(
            Modifier.background(Color(0xff8D8C7B), RoundedCornerShape(16.dp)).padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Add Shoot", style = MaterialTheme.typography.headlineMedium, color = Color(0xffF3F0E8))

            Spacer(32.dp)

            TextField(
                modifier = Modifier.width(300.dp),
                label = { Text("Shoot Name") },
                value = localState.shoot.name,
                onValueChange = localState.onSetName,
            )

            Spacer(16.dp)

            if (addedPhotos == null) {
                Button(
                    modifier = Modifier.width(300.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { showAddShootDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffF3F0E8),
                        contentColor = Color(0xff8D8C7B),
                    )
                ) {
                    Text("Add Photos")
                }

                Spacer(16.dp)
            }

            addedPhotos?.let { addedPhotos ->
                Text("Added Photos: ${addedPhotos.size}")

                Spacer(16.dp)

                Button(
                    modifier = Modifier.width(300.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { localState.onSubmit() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffF3F0E8),
                        contentColor = Color(0xff8D8C7B),
                    )
                ) {
                    Text("Submit")
                }
            }
        }
    }
}
