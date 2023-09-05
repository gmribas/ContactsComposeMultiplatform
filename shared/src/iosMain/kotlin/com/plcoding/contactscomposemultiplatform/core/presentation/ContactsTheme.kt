package com.plcoding.contactscomposemultiplatform.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.plcoding.contactscomposemultiplatform.ui.theme.DarkColorScheme
import com.plcoding.contactscomposemultiplatform.ui.theme.LightColorScheme
import com.plcoding.contactscomposemultiplatform.ui.theme.Typography

@Composable
actual fun ContactsTheme(
    darkTheme: Boolean,
    dynamicColor: Color,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}