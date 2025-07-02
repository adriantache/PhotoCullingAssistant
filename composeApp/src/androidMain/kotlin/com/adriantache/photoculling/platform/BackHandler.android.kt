package com.adriantache.photoculling.platform

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBackPressed: () -> Unit) {
    BackHandler(enabled = enabled) {
        onBackPressed()
    }
}
