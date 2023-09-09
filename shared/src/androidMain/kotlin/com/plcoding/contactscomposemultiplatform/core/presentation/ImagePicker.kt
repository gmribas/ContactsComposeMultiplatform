package com.plcoding.contactscomposemultiplatform.core.presentation

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

actual class ImagePicker(
    private val activity: ComponentActivity
) {

    private lateinit var launcher: ManagedActivityResultLauncher<String, Uri?>
    @Composable
    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
        launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                activity.contentResolver.openInputStream(it)?.use { stream ->
                    onImagePicked(stream.readBytes())
                }
            }
        }
    }

    actual fun pickImage() {
        launcher.launch("image/*")
    }
}