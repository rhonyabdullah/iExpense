package com.mobile.iexpense.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.shimmer.ShimmerBox
import com.mobile.iexpense.core.component.theme.DesignSystem

@Composable
internal fun LoadingStateContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = DesignSystem.dimens.spacingMd),
        verticalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingMd)
    ) {
        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingSm))

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(DesignSystem.dimens.radiusMd)
        ) {
            Column(modifier = Modifier.padding(DesignSystem.dimens.spacingMd)) {
                ShimmerBox(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp),
                    shape = RoundedCornerShape(4.dp)
                )
                Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingSm))
                ShimmerBox(
                    modifier = Modifier
                        .width(180.dp)
                        .height(36.dp),
                    shape = RoundedCornerShape(4.dp)
                )
                Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingMd))
                Row(horizontalArrangement = Arrangement.spacedBy(DesignSystem.dimens.spacingMd)) {
                    ShimmerBox(
                        modifier = Modifier
                            .width(90.dp)
                            .height(16.dp),
                        shape = RoundedCornerShape(4.dp)
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .width(90.dp)
                            .height(16.dp),
                        shape = RoundedCornerShape(4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(DesignSystem.dimens.spacingXs))

        ShimmerBox(
            modifier = Modifier
                .width(140.dp)
                .height(22.dp),
            shape = RoundedCornerShape(4.dp)
        )

        repeat(4) {
            TransactionSkeletonItem()
        }
    }
}
