package com.adriantache.photoculling.presentation.shootsCollection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import org.jetbrains.compose.resources.painterResource
import photocullingassistant.composeapp.generated.resources.Res
import photocullingassistant.composeapp.generated.resources.adrian_tache_white

@Composable
fun ShootsCollectionView(state: ShootsCollectionState.Content) {
    var showDeleteShootDialog: String? by remember { mutableStateOf(null) }

    Column(Modifier.fillMaxSize().padding(top = 32.dp, start = 32.dp, end = 32.dp)) {
        FlowRow(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors().copy(containerColor = Color(0xff5C7B8D))
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp, 150.dp)
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
            }

            state.shoots.shoots.forEach { shoot ->
                AnimatedVisibility(true) {
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors().copy(
                            containerColor = Color(0xff8D5C7B)
                        )
                    ) {
                        val interactionSource = remember { MutableInteractionSource() }

                        Box(
                            modifier = Modifier
                                .size(200.dp, 150.dp)
                                .combinedClickable(
                                    interactionSource = interactionSource,
                                    indication = ripple(true),
                                    onClick = { state.onOpenShoot(shoot.id) },
                                    onLongClick = { showDeleteShootDialog = shoot.id }
                                ),
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
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                modifier = Modifier.height(180.dp),
                painter = painterResource(Res.drawable.adrian_tache_white),
                contentDescription = null,
                tint = Color(0xffEDEEF3)
            )
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
