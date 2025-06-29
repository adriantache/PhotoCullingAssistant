package com.adriantache.photoculling.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

@Composable
expect fun rememberHapticController(): HapticController

interface HapticController {
    fun performHapticFeedback(type: HapticFeedbackType)
}
