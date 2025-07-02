package com.adriantache.photoculling.presentation.shoot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.adriantache.photoculling.BACKGROUND_COLOR
import com.adriantache.photoculling.domain.ui.model.PhotoUi
import com.adriantache.photoculling.domain.ui.model.ShootUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CacheView(
    shoot: ShootUi,
    selectedPhoto: PhotoUi
) {
    val index: Int? by remember { mutableStateOf(null) }

    LaunchedEffect(selectedPhoto) {
        launch {
            delay(2000)

            shoot.photos.indexOfFirst { it.id == selectedPhoto.id }
        }
    }
    index?.let { index ->
        Box(modifier = Modifier.fillMaxSize()) {
            repeat(5) {
                if (it > shoot.photos.size - 1) return@repeat

                val currentIndex = index + it + 1

                AsyncImage(
                    model = shoot.photos[currentIndex].uri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Box(modifier = Modifier.fillMaxSize().background(BACKGROUND_COLOR))
        }
    }
}
