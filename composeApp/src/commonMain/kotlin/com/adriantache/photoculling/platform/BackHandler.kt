package com.adriantache.photoculling.platform

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(enabled: Boolean, onBackPressed: () -> Unit)
