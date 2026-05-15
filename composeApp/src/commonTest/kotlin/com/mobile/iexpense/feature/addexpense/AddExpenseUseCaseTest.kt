package com.mobile.iexpense.feature.addexpense

import com.mobile.iexpense.core.common.result.AppResult
import com.mobile.iexpense.core.domain.model.EntityCategory
import com.mobile.iexpense.core.domain.model.ExpenseModel
import com.mobile.iexpense.core.domain.repository.ExpenseRepository
import com.mobile.iexpense.core.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertIs

class AddExpenseUseCaseTest {

    @Test
    fun `invoke with valid expense returns success`() = runTest {
        val fakeRepo = FakeExpenseRepository()
        val useCase = AddExpenseUseCase(fakeRepo)

        val expense = ExpenseModel(
            id = "1",
            title = "Coffee",
            amount = 4.50,
            category = EntityCategory.ExpenseCategory.FOOD,
            date = 1L,
            note = "Morning coffee"
        )

        val result = useCase(expense)

        assertIs<AppResult.Success<ExpenseModel>>(result)
        assertEquals(expense, (result as AppResult.Success).data)
        assertTrue(fakeRepo.insertedExpenses.contains(expense))
    }

    @Test
    fun `invoke when repository throws returns failure`() = runTest {
        val fakeRepo = FakeExpenseRepository(shouldThrow = true)
        val useCase = AddExpenseUseCase(fakeRepo)

        val expense = ExpenseModel(
            id = "1",
            title = "Coffee",
            amount = 4.50,
            category = EntityCategory.ExpenseCategory.FOOD,
            date = 1L,
            note = null
        )

        val result = useCase(expense)

        assertIs<AppResult.Failure<ExpenseModel>>(result)
    }

    private class FakeExpenseRepository(
        private val shouldThrow: Boolean = false
    ) : ExpenseRepository {
        val insertedExpenses = mutableListOf<ExpenseModel>()

        override fun getExpenses() = throw NotImplementedError()
        override suspend fun getExpenseById(id: String) = throw NotImplementedError()

        override suspend fun addExpense(expense: ExpenseModel): AppResult<ExpenseModel> {
            if (shouldThrow) {
                return AppResult.Failure(RuntimeException("Database error"))
            }
            insertedExpenses.add(expense)
            return AppResult.Success(expense)
        }

        override suspend fun updateExpense(expense: ExpenseModel) = throw NotImplementedError()
        override suspend fun deleteExpense(id: String) = throw NotImplementedError()
    }
}
