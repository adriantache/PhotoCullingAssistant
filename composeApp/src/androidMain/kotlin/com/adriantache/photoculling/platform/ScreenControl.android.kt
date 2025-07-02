package com.adriantache.photoculling.platform

import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
actual fun KeepScreenOnAndBright() {
    val activity = LocalActivity.current

    DisposableEffect(activity) {
        if (activity != null) {
            // 1) keep screen on
            activity.window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )

            // 2) max brightness
            activity.window.attributes = activity.window.attributes.apply {
                screenBrightness = 1f
            }
        }
        onDispose {
            // clean up when this composable leaves
            if (activity != null) {
                activity.window.clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                )
                // restore system default brightness
                activity.window.attributes = activity.window.attributes.apply {
                    screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
                }
            }
        }
    }
}
