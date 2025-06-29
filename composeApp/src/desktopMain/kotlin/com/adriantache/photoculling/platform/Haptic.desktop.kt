package com.adriantache.photoculling.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

@Composable
actual fun rememberHapticController(): HapticController {
    return NoOp
}

private object NoOp : HapticController {
    override fun performHapticFeedback(type: HapticFeedbackType) = Unit
}
