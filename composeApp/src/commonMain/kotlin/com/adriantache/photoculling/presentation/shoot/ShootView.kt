package com.adriantache.photoculling.presentation.shoot

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adriantache.photoculling.BACKGROUND_COLOR
import com.adriantache.photoculling.domain.state.ShootState
import com.adriantache.photoculling.platform.KeepScreenOnAndBright
import com.adriantache.photoculling.platform.rememberHapticController
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun ShootView(localState: ShootState.Content) {
    val hapticController = rememberHapticController()

    KeepScreenOnAndBright()

    localState.shoot.selectedPhoto?.let { selectedPhoto ->
        var dragOffsetX by remember { mutableStateOf(0f) }
        val threshold = 150f // TODO: set threshold based on platform
        val animatedOffsetX by animateFloatAsState(targetValue = dragOffsetX)

        Box(
            modifier = Modifier.fillMaxSize()
                .background(BACKGROUND_COLOR)
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) },
            contentAlignment = Alignment.Center,
        ) {
            CacheView(localState.shoot, selectedPhoto)

            CircularProgressIndicator(color = Color(0xffF3EDEB))

            AsyncImage(
                model = selectedPhoto.uri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            dragOffsetX += dragAmount.x
                            change.consume()

                            if (abs(dragOffsetX) > threshold) {
                                hapticController.performHapticFeedback(HapticFeedbackType.SegmentTick)
                            }
                        },
                        onDragEnd = {
                            when {
                                dragOffsetX < -threshold -> localState.onNavigateToNextPhoto(true)
                                dragOffsetX > threshold -> localState.onNavigateToNextPhoto(false)
                            }
                            // reset offset (animate back to zero)
                            dragOffsetX = 0f
                        },
                        onDragCancel = { dragOffsetX = 0f }
                    )
                }
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            VotingView(rating = localState.shoot.selectedPhoto?.rating, onVote = { localState.onSetRating(it) })

            Box(modifier = Modifier.weight(1f))

            VotingView(rating = localState.shoot.selectedPhoto?.rating, onVote = { localState.onSetRating(it) })
        }

        Text(
            text = "${localState.shoot.progress + 1}/${localState.shoot.photos.size}",
            modifier = Modifier.padding(16.dp)
        )
    }
}
