package com.mobile.iexpense.core.domain.repository

import com.mobile.iexpense.core.common.result.AppResult
import com.mobile.iexpense.core.domain.model.ExpenseModel
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(): Flow<AppResult<List<ExpenseModel>>>
    suspend fun getExpenseById(id: String): AppResult<ExpenseModel>
    suspend fun addExpense(expense: ExpenseModel): AppResult<ExpenseModel>
    suspend fun updateExpense(expense: ExpenseModel): AppResult<ExpenseModel>
    suspend fun deleteExpense(id: String): AppResult<Unit>
}