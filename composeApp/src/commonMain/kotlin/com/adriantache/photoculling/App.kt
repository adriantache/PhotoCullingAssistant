package com.adriantache.photoculling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adriantache.photoculling.presentation.navigation.NavigationView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
            NavigationView()
        }
    }
}
