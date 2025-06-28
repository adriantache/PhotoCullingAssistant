package com.adriantache.photoculling

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.documentfile.provider.DocumentFile
import com.adriantache.photoculling.platform.PhotoPickerHelper

class MainActivity : ComponentActivity() {
    private lateinit var pickFolderLauncher: ActivityResultLauncher<Uri?>
    private val photoPickerHelper = PhotoPickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        registerResultLauncher()

        setContent {
            App()
        }
    }

    private fun registerResultLauncher() {
        pickFolderLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocumentTree()
        ) { treeUri: Uri? ->
            if (treeUri == null) return@registerForActivityResult

            contentResolver.takePersistableUriPermission(
                /* uri = */ treeUri,
                /* modeFlags = */ Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
            val imageUris = listImageUrisInFolder(this, treeUri)

            photoPickerHelper.pickerResults.value = imageUris.map { it.toString() }
        }

        photoPickerHelper.launchPicker = { pickFolderLauncher.launch(null) }
    }

    fun listImageUrisInFolder(context: Context, treeUri: Uri): List<Uri> {
        val pickedDir = DocumentFile.fromTreeUri(context, treeUri) ?: return emptyList()
        val result = mutableListOf<Uri>()
        fun scan(folder: DocumentFile) {
            folder.listFiles().forEach { doc ->
                if (doc.isDirectory) {
                    scan(doc)
                } else {
                    val mime = doc.type ?: ""
                    if (mime.startsWith("image/")) {
                        result += doc.uri
                    }
                }
            }
        }
        scan(pickedDir)
        return result
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
