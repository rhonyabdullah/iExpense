package com.mobile.iexpense.core.domain.model

data class ExpenseModel(
    val id: String,
    val title: String,
    val amount: Double,
    val category: EntityCategory.ExpenseCategory,
    val date: Long,
    val note: String? = null
)