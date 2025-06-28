package com.adriantache.photoculling.platform

import kotlinx.coroutines.flow.Flow

expect fun pickPhotos()

expect fun getPhotoPickerResults(): Flow<List<String>?>
