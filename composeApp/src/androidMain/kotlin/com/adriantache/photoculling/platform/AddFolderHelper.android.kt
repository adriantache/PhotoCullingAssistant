package com.adriantache.photoculling.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

actual fun pickPhotos() {
    PhotoPickerHelper.launchPicker?.invoke()
}

actual fun getPhotoPickerResults(): Flow<List<String>?> = PhotoPickerHelper.pickerResults

object PhotoPickerHelper {
    var launchPicker: (() -> Unit)? = null

    var pickerResults: MutableStateFlow<List<String>?> = MutableStateFlow(null)
}
