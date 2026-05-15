package com.mobile.iexpense.core.component.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
fun AppToastCard(
    data: AppToastData,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (data.type) {
        AppToastType.Success -> Color(0xFF2E7D32)
        AppToastType.Error -> Color(0xFFC62828)
        AppToastType.Warning -> Color(0xFFF9A825)
        AppToastType.Info -> Color(0xFF1565C0)
    }

    val contentColor = when (data.type) {
        AppToastType.Warning -> Color.Black
        else -> Color.White
    }

    Box(
        modifier = modifier
            .padding(16.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = data.message,
            style = DesignSystem.typography.bodyMd,
            color = contentColor
        )
    }
}
