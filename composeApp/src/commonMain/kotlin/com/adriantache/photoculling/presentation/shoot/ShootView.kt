package com.adriantache.photoculling.presentation.shoot

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import coil3.compose.AsyncImage
import com.adriantache.photoculling.domain.state.ShootState
import kotlin.math.roundToInt

@Composable
fun ShootView(localState: ShootState.Content) {
    localState.shoot.selectedPhoto?.let { selectedPhoto ->
        var dragOffsetX by remember { mutableStateOf(0f) }
        val threshold = 150f // TODO: set threshold based on platform
        val animatedOffsetX by animateFloatAsState(targetValue = dragOffsetX)

        Box(
            modifier = Modifier.fillMaxSize()
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
        ) {
            AsyncImage(
                model = selectedPhoto.uri.fixUri(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            dragOffsetX += dragAmount.x
                            change.consume()
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
        Text(text = "${localState.shoot.progress + 1}/${localState.shoot.photos.size}")
    }
}

private fun String.fixUri(): String {
    return this.replace("file:/", "file:///")
}
