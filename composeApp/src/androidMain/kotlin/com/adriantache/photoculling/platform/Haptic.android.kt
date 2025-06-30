package com.adriantache.photoculling.platform

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.core.content.getSystemService

@Composable
actual fun rememberHapticController(): HapticController {
    return object : HapticController {
        val vibrator = remember { AndroidContextHolder.appContext.getSystemService<Vibrator>() }

        override fun performHapticFeedback(type: HapticFeedbackType) {
            val (duration, amplitude) = getConfig(type)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator?.vibrate(
                    VibrationEffect.createOneShot(
                        duration,
                        amplitude,
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(20L)
            }
        }

        private fun getConfig(type: HapticFeedbackType): Pair<Long, Int> {
            return when (type) {
                HapticFeedbackType.Confirm -> 100L to 128
                HapticFeedbackType.Reject -> 100L to VibrationEffect.DEFAULT_AMPLITUDE
                HapticFeedbackType.SegmentTick -> 50L to VibrationEffect.DEFAULT_AMPLITUDE

                // fallback for any other / future types
                else -> 50L to VibrationEffect.DEFAULT_AMPLITUDE
            }
        }
    }
}
