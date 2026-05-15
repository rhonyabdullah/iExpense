package com.mobile.iexpense.core.data.repository

import com.mobile.iexpense.core.common.result.AppResult
import com.mobile.iexpense.core.data.mapper.toDomainModel
import com.mobile.iexpense.core.data.mapper.toEntity
import com.mobile.iexpense.core.data.mapper.toNetworkModel
import com.mobile.iexpense.core.database.source.ExpenseDatabaseDataSource
import com.mobile.iexpense.core.domain.model.ExpenseModel
import com.mobile.iexpense.core.domain.repository.ExpenseRepository
import com.mobile.iexpense.core.network.source.ExpenseNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val databaseDataSource: ExpenseDatabaseDataSource
) : ExpenseRepository {

    override fun getExpenses(): Flow<AppResult<List<ExpenseModel>>> = flow {
        emit(AppResult.Loading())

        databaseDataSource.getAllExpenses().collect { entities ->
            val models = entities.map { it.toDomainModel() }
            emit(AppResult.Success(models))
        }
    }

    override suspend fun getExpenseById(id: String): AppResult<ExpenseModel> {
        return try {
            val entity = databaseDataSource.getExpenseById(id)
            if (entity != null) {
                AppResult.Success(entity.toDomainModel())
            } else {
                AppResult.Failure(IllegalStateException("Expense not found"))
            }
        } catch (e: Exception) {
            AppResult.Failure(e)
        }
    }

    override suspend fun addExpense(expense: ExpenseModel): AppResult<ExpenseModel> {
        return try {
            databaseDataSource.insertExpense(expense.toEntity())
            AppResult.Success(expense)
        } catch (e: Exception) {
            AppResult.Failure(e)
        }
    }

    override suspend fun updateExpense(expense: ExpenseModel): AppResult<ExpenseModel> {
        return try {
            databaseDataSource.updateExpense(expense.toEntity())
            AppResult.Success(expense)
        } catch (e: Exception) {
            AppResult.Failure(e)
        }
    }

    override suspend fun deleteExpense(id: String): AppResult<Unit> {
        return try {
            databaseDataSource.deleteExpenseById(id)
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Failure(e)
        }
    }
}
