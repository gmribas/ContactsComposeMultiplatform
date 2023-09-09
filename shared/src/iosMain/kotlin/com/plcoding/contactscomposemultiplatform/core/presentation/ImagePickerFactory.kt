package com.plcoding.contactscomposemultiplatform.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIViewController

actual class ImagePickerFactory(
    private val controller: UIViewController
) {
    @Composable
    actual fun createPicker(): ImagePicker {
        return remember {
            ImagePicker(controller)
        }
    }
}