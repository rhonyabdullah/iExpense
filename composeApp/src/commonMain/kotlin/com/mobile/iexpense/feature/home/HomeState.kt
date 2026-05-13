package com.mobile.iexpense.feature.home

data class HomeState(
    val isLoading: Boolean = false,
    val expenses: List<ExpenseUi> = emptyList(),
    val error: String? = null
)

data class ExpenseUi(
    val id: String,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String
)