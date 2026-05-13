package com.mobile.iexpense.core.database.source

import com.mobile.iexpense.core.database.dao.ExpenseDao
import com.mobile.iexpense.core.database.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseDatabaseDataSource(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()

    suspend fun getExpenseById(id: String): ExpenseEntity? = expenseDao.getExpenseById(id)

    suspend fun insertExpense(expense: ExpenseEntity) = expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: ExpenseEntity) = expenseDao.updateExpense(expense)

    suspend fun deleteExpenseById(id: String) = expenseDao.deleteExpenseById(id)
}