package com.mobile.iexpense.core.network.api

import com.mobile.iexpense.core.network.model.NetworkExpense

interface ExpenseApiService {
    suspend fun getExpenses(): List<NetworkExpense>
    suspend fun getExpenseById(id: String): NetworkExpense?
    suspend fun addExpense(expense: NetworkExpense): NetworkExpense
    suspend fun updateExpense(expense: NetworkExpense): NetworkExpense
    suspend fun deleteExpense(id: String)
}