package com.mobile.iexpense.feature.home

import com.mobile.iexpense.core.domain.model.EntityCategory

data class HomeState(
    val isLoading: Boolean = false,
    val expenses: List<ExpenseUi> = emptyList(),
    val totalThisMonth: Double = 0.0,
    val error: String? = null
)

data class ExpenseUi(
    val id: String,
    val title: String,
    val amount: Double,
    val category: EntityCategory.ExpenseCategory,
    val date: String
)
