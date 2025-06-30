package com.adriantache.photoculling.platform

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.disk.directory
import java.io.File

actual val coilLoader: ImageLoader? by lazy {
    ImageLoader.Builder(AndroidContextHolder.appContext)
        .diskCache {
            DiskCache.Builder()
                .directory(File("imagecache"))
                .maxSizeBytes(1000L * 1024 * 1024)
                .build()
        }
        .build()
}
actual val coilPlatformContext: PlatformContext?
    get() = AndroidContextHolder.appContext
