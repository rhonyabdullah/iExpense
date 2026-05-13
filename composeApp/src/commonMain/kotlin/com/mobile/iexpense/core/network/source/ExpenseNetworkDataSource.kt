package com.mobile.iexpense.core.network.source

import com.mobile.iexpense.core.network.api.ExpenseApiService
import com.mobile.iexpense.core.network.model.NetworkExpense

class ExpenseNetworkDataSource(
    private val apiService: ExpenseApiService
) {
    suspend fun getExpenses(): List<NetworkExpense> = apiService.getExpenses()
    suspend fun getExpenseById(id: String): NetworkExpense? = apiService.getExpenseById(id)
    suspend fun addExpense(expense: NetworkExpense): NetworkExpense = apiService.addExpense(expense)
    suspend fun updateExpense(expense: NetworkExpense): NetworkExpense = apiService.updateExpense(expense)
    suspend fun deleteExpense(id: String) = apiService.deleteExpense(id)
}