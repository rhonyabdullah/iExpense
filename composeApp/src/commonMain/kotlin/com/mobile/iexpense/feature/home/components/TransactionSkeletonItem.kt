package com.mobile.iexpense.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.shimmer.ShimmerBox
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
internal fun TransactionSkeletonItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingMd)
        ) {
            ShimmerBox(
                modifier = Modifier.size(40.dp),
                shape = CircleShape
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ShimmerBox(
                    modifier = Modifier
                        .width(120.dp)
                        .height(18.dp),
                    shape = RoundedCornerShape(4.dp)
                )
                ShimmerBox(
                    modifier = Modifier
                        .width(80.dp)
                        .height(14.dp),
                    shape = RoundedCornerShape(4.dp)
                )
            }
        }
        ShimmerBox(
            modifier = Modifier
                .width(64.dp)
                .height(18.dp),
            shape = RoundedCornerShape(4.dp)
        )
    }
}
