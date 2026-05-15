package com.mobile.iexpense.feature.addexpense.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.iexpense.core.component.theme.DesignSystem
import com.mobile.iexpense.core.component.theme.Dimens
import iexpense.composeapp.generated.resources.Res
import iexpense.composeapp.generated.resources.add_expense_error_date_invalid
import iexpense.composeapp.generated.resources.add_expense_field_date
import iexpense.composeapp.generated.resources.add_expense_field_date_select
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DateField(
    date: Long,
    error: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val instant = Instant.fromEpochMilliseconds(date)
    val timeZone = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val dateText = "${timeZone.dayOfMonth.toString().padStart(2, '0')}/" +
            "${timeZone.month.number.toString().padStart(2, '0')}/${timeZone.year}"

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = DesignSystem.colors.backgroundSecondary,
                    shape = RoundedCornerShape(DesignSystem.dimens.radiusMd)
                )
                .border(
                    width = 1.dp,
                    color = if (error) DesignSystem.colors.borderCritical else DesignSystem.colors.borderSecondary,
                    shape = RoundedCornerShape(DesignSystem.dimens.radiusMd)
                )
                .clickable(onClick = onClick)
                .padding(
                    horizontal = DesignSystem.dimens.spacingMd,
                    vertical = DesignSystem.dimens.spacingMd
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.add_expense_field_date),
                    style = DesignSystem.typography.bodyMd,
                    color = DesignSystem.colors.textSecondary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = dateText,
                    style = DesignSystem.typography.bodyMd,
                    color = DesignSystem.colors.textPrimary,
                    modifier = Modifier.padding(end = DesignSystem.dimens.spacingSm)
                )
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(Res.string.add_expense_field_date_select),
                    tint = DesignSystem.colors.iconSecondary
                )
            }
        }
        if (error) {
            Text(
                text = stringResource(Res.string.add_expense_error_date_invalid),
                style = DesignSystem.typography.bodySm,
                color = DesignSystem.colors.textCritical,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}
