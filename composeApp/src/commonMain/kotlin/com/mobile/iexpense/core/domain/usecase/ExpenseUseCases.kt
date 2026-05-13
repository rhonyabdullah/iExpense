package com.mobile.iexpense.core.domain.usecase

import com.mobile.iexpense.core.common.result.AppResult
import com.mobile.iexpense.core.domain.model.ExpenseModel
import com.mobile.iexpense.core.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpensesUseCase(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<AppResult<List<ExpenseModel>>> {
        return repository.getExpenses()
    }
}

class GetExpenseByIdUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(id: String): AppResult<ExpenseModel> {
        return repository.getExpenseById(id)
    }
}

class AddExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: ExpenseModel): AppResult<ExpenseModel> {
        return repository.addExpense(expense)
    }
}

class UpdateExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: ExpenseModel): AppResult<ExpenseModel> {
        return repository.updateExpense(expense)
    }
}

class DeleteExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(id: String): AppResult<Unit> {
        return repository.deleteExpense(id)
    }
}