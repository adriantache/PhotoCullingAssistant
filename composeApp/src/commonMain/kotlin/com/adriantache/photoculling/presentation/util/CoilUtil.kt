package com.adriantache.photoculling.presentation.util

import androidx.compose.ui.InternalComposeUiApi
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.size.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(InternalComposeUiApi::class)
suspend fun preloadImage(
    context: PlatformContext,
    imageLoader: ImageLoader,
    data: Any,
) {
    val request = ImageRequest.Builder(context)
        .data(data)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .size(Size.ORIGINAL)
        .build()

    withContext(Dispatchers.IO) {
        imageLoader.enqueue(request)
    }
}
