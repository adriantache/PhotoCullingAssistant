package com.adriantache.photoculling.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import java.io.File
import javax.swing.JFileChooser

private val pickerResults: MutableStateFlow<List<String>?> = MutableStateFlow(null)

actual fun getPhotoPickerResults(): Flow<List<String>?> = pickerResults

actual fun pickPhotos() {
    runBlocking {
        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            dialogTitle = "Select a folder of photos"
        }

        val result = chooser.showOpenDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) {
            pickerResults.value = null
        }

        val folder = chooser.selectedFile
        if (!folder.isDirectory) pickerResults.value = null

        val files = folder.walkTopDown()
            .filter { it.isFile && isImageFile(it) }
            .map { it.toURI().toString() }
            .toList()

        pickerResults.value = files
    }
}

private val IMAGE_EXTENSIONS = setOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "heic")

private fun isImageFile(file: File): Boolean {
    val ext = file.extension.lowercase()
    return ext in IMAGE_EXTENSIONS
}

actual fun getFilePath(fileName: String): String {
    val home = System.getProperty("user.home")
        ?: throw IllegalStateException("No user.home property")

    return "$home/Downloads/PhotoCullingAssistant/$fileName"
}
