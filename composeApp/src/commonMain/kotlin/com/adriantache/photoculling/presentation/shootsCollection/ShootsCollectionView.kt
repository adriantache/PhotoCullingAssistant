package com.adriantache.photoculling.presentation.shootsCollection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.adriantache.photoculling.domain.state.ShootsCollectionState

@Composable
fun ShootsCollectionView(state: ShootsCollectionState.Content) {
    var showDeleteShootDialog: String? by remember { mutableStateOf(null) }

    Column(Modifier.fillMaxSize().padding(32.dp)) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp, 150.dp)
                    .background(Color(0xff5C7B8D), RoundedCornerShape(16.dp))
                    .clickable { state.onAddShoot() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    tint = Color(0xccECF1F4),
                    modifier = Modifier.size(48.dp),
                )
            }

            state.shoots.shoots.forEach { shoot ->
                AnimatedVisibility(true) {
                    Box(
                        modifier = Modifier
                            .size(200.dp, 150.dp)
                            .background(Color(0xff8D5C7B), RoundedCornerShape(16.dp))
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { _ -> showDeleteShootDialog = shoot.id },
                                    onTap = { _ -> state.onOpenShoot(shoot.id) }
                                )
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = shoot.name.uppercase(),
                            color = Color(0xccF4ECF2),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
            }
        }

        showDeleteShootDialog?.let { shootId ->
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = {
                        state.onDeleteShoot(shootId)
                        showDeleteShootDialog = null
                    }) { Text("Delete") }
                },
                dismissButton = { Button(onClick = { showDeleteShootDialog = null }) { Text("Cancel") } },
                title = { Text("Delete shoot?") },
            )
        }
    }
}
