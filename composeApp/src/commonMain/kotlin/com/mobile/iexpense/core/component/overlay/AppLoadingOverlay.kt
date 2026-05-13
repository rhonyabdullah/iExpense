package com.mobile.iexpense.core.component.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
fun AppLoadingOverlay(visible: Boolean) {
    if (visible) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = DesignSystem.colors.inverseSurface,
                        shape = CircleShape
                    )
                    .size(64.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = DesignSystem.colors.inverseOnSurface,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}