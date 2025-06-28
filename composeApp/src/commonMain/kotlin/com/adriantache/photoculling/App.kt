package com.adriantache.photoculling

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adriantache.photoculling.presentation.navigation.NavigationView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold {
            NavigationView(
                modifier = Modifier.padding(it).systemBarsPadding()
            )
        }
    }
}
