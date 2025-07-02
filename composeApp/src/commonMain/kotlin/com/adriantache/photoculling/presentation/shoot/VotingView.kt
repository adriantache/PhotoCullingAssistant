package com.adriantache.photoculling.presentation.shoot

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.adriantache.photoculling.platform.rememberHapticController
import com.adriantache.photoculling.presentation.util.Spacer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(InternalResourceApi::class)
@Composable
fun VotingView(
    rating: Int?,
    onVote: (rating: Int) -> Unit,
) {
    // TODO: try to make the compose haptic work?
    val backupHaptic = rememberHapticController()

    val iconSize = 32.dp
    val iconSpacing = 4.dp

    var votingValue: Int? by remember { mutableStateOf(rating) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    var isUiVisible by remember { mutableStateOf(true) }
    val uiAlpha by animateFloatAsState(targetValue = if (isUiVisible) 1f else 0f)

    val starThreshold = 100f // TODO: set threshold based on platform
    val largeThreshold = 300f // TODO: set threshold based on platform

    val selectedColor = Color.White

    LaunchedEffect(rating) {
        votingValue = rating
        isUiVisible = true
    }

    LaunchedEffect(isUiVisible) {
        if (!isUiVisible) return@LaunchedEffect

        launch {
            delay(3000)

            isUiVisible = false
        }
    }

    LaunchedEffect(votingValue) {
        val vote = votingValue ?: return@LaunchedEffect

        onVote(vote)

        val hapticGesture = when (vote) {
            6 -> HapticFeedbackType.Confirm
            -1 -> HapticFeedbackType.Reject
            else -> HapticFeedbackType.SegmentTick
        }

        backupHaptic.performHapticFeedback(hapticGesture)
    }

    fun getStarVisibility(index: Int): Boolean {
        if (votingValue == null || votingValue == 6 || votingValue == -1) return false

        return votingValue!! >= index + 1
    }

    Column(
        modifier = Modifier.fillMaxHeight()
            .width(150.dp)
            .alpha(uiAlpha)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        // reset if you want each gesture to start fresh
                        dragOffsetY = 0f
                    },
                    onDrag = { change, dragAmount ->
                        dragOffsetY += dragAmount.y
                        change.consume()

                        val threshold = if (
                            (votingValue == 1 && dragOffsetY > 0) || (votingValue == 5 && dragOffsetY < 0) ||
                            (votingValue == -1 && dragOffsetY < 0) || (votingValue == 6 && dragOffsetY < 0)
                        ) {
                            largeThreshold
                        } else {
                            starThreshold
                        }

                        // as long as we’ve crossed the +threshold, increment and pull the offset back
                        while (dragOffsetY > threshold) {
                            votingValue = ((votingValue ?: 0) - 1).coerceAtLeast(-1)
                            dragOffsetY -= threshold
                        }

                        // as long as we’ve crossed the -threshold, decrement and pull the offset back
                        while (dragOffsetY < -threshold) {
                            votingValue = ((votingValue ?: 0) + 1).coerceIn(0, 6)
                            dragOffsetY += threshold
                        }
                    },
                    onDragEnd = {
                        // Optional: reset the offset so next gesture really starts from zero
                        dragOffsetY = 0f
                    },
                    onDragCancel = {
                        dragOffsetY = 0f
                    }
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val durationMillis = 800
        AnimatedVisibility(
            visible = votingValue == 6,
            enter = expandVertically(tween(durationMillis / 2)) + fadeIn(tween(durationMillis)),
            exit = fadeOut(tween(durationMillis)) + shrinkVertically(tween(durationMillis / 2)),
        ) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Favorite",
                tint = selectedColor,
                modifier = Modifier.size(iconSize * 2)
            )
        }

        repeat(5) {
            AnimatedVisibility(getStarVisibility(5 - it - 1)) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Vote $it",
                    tint = selectedColor,
                    modifier = Modifier.size(iconSize)
                )
            }

            Spacer(iconSpacing)
        }

        AnimatedVisibility(
            visible = votingValue == -1,
            enter = fadeIn(tween(durationMillis)) + expandVertically(tween(durationMillis / 2)),
            exit = fadeOut(tween(durationMillis)) + shrinkVertically(tween(durationMillis / 2)),
        ) {
            Icon(
                imageVector = Icons.Outlined.Cancel,
                contentDescription = "Decline",
                tint = selectedColor,
                modifier = Modifier.size(iconSize * 2)
            )
        }
    }
}
