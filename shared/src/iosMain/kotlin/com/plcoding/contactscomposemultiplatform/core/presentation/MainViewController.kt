package com.plcoding.contactscomposemultiplatform.core.presentation

import App
import androidx.compose.ui.window.ComposeUIViewController
import com.plcoding.contactscomposemultiplatform.di.AppModule
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

@Suppress("FunctionName")
fun MainViewController() = ComposeUIViewController {
    val isDartTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
            UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App(darkTheme = isDartTheme, dynamicColor = false, appModule = AppModule())
}