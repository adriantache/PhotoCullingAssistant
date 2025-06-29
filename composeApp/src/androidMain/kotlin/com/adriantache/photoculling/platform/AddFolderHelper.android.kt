package com.adriantache.photoculling.platform

import android.content.Context
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

actual fun getFilePath(fileName: String): String {
    return AndroidContextHolder.appContext.filesDir.absolutePath + "/$fileName"
}

object AndroidContextHolder {
    // assign this from your Application.onCreate()
    lateinit var appContext: Context
}
