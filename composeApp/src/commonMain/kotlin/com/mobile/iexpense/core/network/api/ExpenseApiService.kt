package com.mobile.iexpense.core.network.api

import com.mobile.iexpense.core.network.model.NetworkExpense

interface ExpenseApiService {
    suspend fun getExpenses(): List<NetworkExpense>
    suspend fun getExpenseById(id: String): NetworkExpense?
    suspend fun addExpense(expense: NetworkExpense): NetworkExpense
    suspend fun updateExpense(expense: NetworkExpense): NetworkExpense
    suspend fun deleteExpense(id: String)
}

internal class ExpenseApiServiceImpl() : ExpenseApiService {
    override suspend fun getExpenses(): List<NetworkExpense> {
        TODO("Not yet implemented")
    }

    override suspend fun getExpenseById(id: String): NetworkExpense? {
        TODO("Not yet implemented")
    }

    override suspend fun addExpense(expense: NetworkExpense): NetworkExpense {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(expense: NetworkExpense): NetworkExpense {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(id: String) {
        TODO("Not yet implemented")
    }

}
