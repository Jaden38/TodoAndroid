package com.esgi.todoapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ThemeToggle(
    isDarkTheme: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon: ImageVector = if (isDarkTheme) {
        Icons.Outlined.LightMode
    } else {
        Icons.Outlined.DarkMode
    }

    val contentDescription = if (isDarkTheme) {
        "Switch to light theme"
    } else {
        "Switch to dark theme"
    }

    IconButton(
        onClick = onToggle,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}