package com.adriantache.photoculling.platform

import kotlinx.coroutines.flow.Flow

// TODO: support subfolders for saving the final output files
expect fun getFilePath(fileName: String): String

expect fun pickPhotos()

expect fun getPhotoPickerResults(): Flow<List<String>?>
