package com.mobile.iexpense.feature.addexpense

import com.mobile.iexpense.core.common.currentTimeMillis
import com.mobile.iexpense.core.domain.model.EntityCategory

internal data class AddExpenseState(
    val title: String = "",
    val amount: String = "",
    val date: Long = currentTimeMillis(),
    val category: EntityCategory.ExpenseCategory = EntityCategory.ExpenseCategory.FOOD,
    val notes: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val titleError: Boolean = false,
    val amountError: Boolean = false,
    val dateError: Boolean = false
)
