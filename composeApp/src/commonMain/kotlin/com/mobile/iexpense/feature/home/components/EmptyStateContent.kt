package com.mobile.iexpense.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.empty_state_illustration
import iexpense.composeapp.generated.resources.home_empty_state_description
import iexpense.composeapp.generated.resources.home_empty_state_subtitle
import iexpense.composeapp.generated.resources.home_empty_state_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EmptyStateContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = DesignSystem.dimens.spacingLg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(256.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = DesignSystem.colors.backgroundSecondary.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
            )
            Image(
                painter = painterResource(Res.drawable.empty_state_illustration),
                contentDescription = stringResource(Res.string.home_empty_state_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(245.dp)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = DesignSystem.colors.backgroundPrimary,
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacing3xl))

        Text(
            text = stringResource(Res.string.home_empty_state_title),
            style = DesignSystem.typography.headingLg.copy(fontWeight = FontWeight.Bold),
            color = DesignSystem.colors.textPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingMd))

        Text(
            text = stringResource(Res.string.home_empty_state_subtitle),
            style = DesignSystem.typography.bodyLg,
            color = DesignSystem.colors.textSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingXl))
    }
}
