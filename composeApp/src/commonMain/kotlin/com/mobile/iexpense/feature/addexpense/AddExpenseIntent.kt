package com.mobile.iexpense.feature.addexpense

import com.mobile.iexpense.core.domain.model.EntityCategory

internal sealed interface AddExpenseIntent {
    data object OnInit : AddExpenseIntent
    data class TitleChanged(val title: String) : AddExpenseIntent
    data class AmountChanged(val amount: String) : AddExpenseIntent
    data class DateChanged(val date: Long) : AddExpenseIntent
    data class CategoryChanged(val category: EntityCategory.ExpenseCategory) : AddExpenseIntent
    data class NotesChanged(val notes: String) : AddExpenseIntent
    data object SaveExpense : AddExpenseIntent
    data object DismissError : AddExpenseIntent
    data object NavigateBack : AddExpenseIntent
}
