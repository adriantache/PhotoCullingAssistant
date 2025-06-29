package com.adriantache.photoculling.platform

import android.os.Build
import android.os.VibrationEffect
import android.os.VibrationEffect.EFFECT_TICK
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.core.content.getSystemService

@Composable
actual fun rememberHapticController(): HapticController {
    return object : HapticController {
        val vibrator = remember { AndroidContextHolder.appContext.getSystemService<Vibrator>() }

        override fun performHapticFeedback(type: HapticFeedbackType) { // TODO: implement type?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator?.vibrate(VibrationEffect.createPredefined(EFFECT_TICK))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(20L)
            }
        }
    }
}
