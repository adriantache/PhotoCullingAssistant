package com.adriantache.photoculling.presentation.shoot

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.adriantache.photoculling.platform.rememberHapticController
import com.adriantache.photoculling.presentation.util.Spacer
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(InternalResourceApi::class)
@Composable
fun VotingView(onVote: (rating: Int) -> Unit) {
    // TODO: figure out the haptic situation :/ 
    val haptic = LocalHapticFeedback.current
    val backupHaptic = rememberHapticController()

    val iconSize = 32.dp
    val iconSpacing = 8.dp

    var votingValue: Int? by remember { mutableStateOf(null) }
    var dragOffsetY by remember { mutableStateOf(0f) }
    val threshold = 100f // TODO: set threshold based on platform

    val selectedColor = Color.White
    val deselectedColor = Color.LightGray.copy(0.5f)


    LaunchedEffect(votingValue) {
        if (votingValue == null) return@LaunchedEffect

        haptic.performHapticFeedback(HapticFeedbackType.SegmentTick)
        backupHaptic.performHapticFeedback(HapticFeedbackType.SegmentTick)
    }

    fun getIconColor(index: Int, currentValue: Int?): Color {
        return when {
            currentValue == null -> deselectedColor
            index == 0 && currentValue == 0 -> selectedColor
            index == 0 && currentValue != 0 -> deselectedColor
            index == 7 && currentValue == 7 -> selectedColor
            index == 7 && currentValue != 7 -> deselectedColor
            index <= currentValue -> selectedColor
            else -> deselectedColor
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight().width(150.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        // reset if you want each gesture to start fresh
                        dragOffsetY = 0f
                    },
                    onDrag = { change, dragAmount ->
                        dragOffsetY += dragAmount.y
                        change.consume()

                        // as long as we’ve crossed the +threshold, increment and pull the offset back
                        while (dragOffsetY > threshold) {
                            votingValue = ((votingValue ?: 0) - 1).coerceAtLeast(-1)
                            dragOffsetY -= threshold
                        }

                        // as long as we’ve crossed the -threshold, decrement and pull the offset back
                        while (dragOffsetY < -threshold) {
                            votingValue = ((votingValue ?: 0) + 1).coerceIn(1, 7)
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
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "Favorite",
            tint = getIconColor(7, votingValue),
            modifier = Modifier.size(iconSize)
        )

        Spacer(iconSpacing)

        repeat(5) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Vote $it",
                tint = getIconColor(5 - it, votingValue),
                modifier = Modifier.size(iconSize)
            )

            Spacer(iconSpacing)
        }

        Icon(
            imageVector = Icons.Outlined.Cancel,
            contentDescription = "Decline",
            tint = getIconColor(0, votingValue),
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview
@Composable
fun VotingViewPreview() {
    MaterialTheme {
        VotingView {}
    }
}
