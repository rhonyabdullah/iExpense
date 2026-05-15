package com.mobile.iexpense.feature.addexpense

import com.mobile.iexpense.core.common.result.AppResult
import com.mobile.iexpense.core.domain.model.EntityCategory
import com.mobile.iexpense.core.domain.model.ExpenseModel
import com.mobile.iexpense.core.domain.repository.ExpenseRepository
import com.mobile.iexpense.core.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AddExpenseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakeExpenseRepository
    private lateinit var addExpenseUseCase: AddExpenseUseCase
    private lateinit var viewModel: AddExpenseViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeExpenseRepository()
        addExpenseUseCase = AddExpenseUseCase(fakeRepository)
        viewModel = AddExpenseViewModel(
            addExpenseUseCase = addExpenseUseCase
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onInit sets isLoading to false`() = runTest {
        viewModel.dispatch(AddExpenseIntent.OnInit)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `TitleChanged updates title and clears error`() = runTest {
        viewModel.dispatch(AddExpenseIntent.TitleChanged("Coffee"))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals("Coffee", state.title)
        assertNull(state.titleError)
    }

    @Test
    fun `AmountChanged updates amount and clears error`() = runTest {
        viewModel.dispatch(AddExpenseIntent.AmountChanged("12.50"))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals("12.50", state.amount)
        assertNull(state.amountError)
    }

    @Test
    fun `CategoryChanged updates category`() = runTest {
        viewModel.dispatch(AddExpenseIntent.CategoryChanged(EntityCategory.ExpenseCategory.TRANSPORT))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals(EntityCategory.ExpenseCategory.TRANSPORT, state.category)
    }

    @Test
    fun `NotesChanged updates notes`() = runTest {
        viewModel.dispatch(AddExpenseIntent.NotesChanged("Business trip"))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals("Business trip", state.notes)
    }

    @Test
    fun `SaveExpense with blank title shows title error`() = runTest {
        viewModel.dispatch(AddExpenseIntent.TitleChanged(""))
        viewModel.dispatch(AddExpenseIntent.AmountChanged("10.0"))
        viewModel.dispatch(AddExpenseIntent.SaveExpense)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertNotNull(state.titleError)
        assertFalse(state.isLoading)
    }

    @Test
    fun `SaveExpense with invalid amount shows amount error`() = runTest {
        viewModel.dispatch(AddExpenseIntent.TitleChanged("Lunch"))
        viewModel.dispatch(AddExpenseIntent.AmountChanged("-5.0"))
        viewModel.dispatch(AddExpenseIntent.SaveExpense)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertNotNull(state.amountError)
        assertFalse(state.isLoading)
    }

    @Test
    fun `SaveExpense with zero amount shows amount error`() = runTest {
        viewModel.dispatch(AddExpenseIntent.TitleChanged("Lunch"))
        viewModel.dispatch(AddExpenseIntent.AmountChanged("0"))
        viewModel.dispatch(AddExpenseIntent.SaveExpense)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertNotNull(state.amountError)
    }

    @Test
    fun `SaveExpense with valid data triggers success effects`() = runTest {
        viewModel.dispatch(AddExpenseIntent.TitleChanged("Dinner"))
        viewModel.dispatch(AddExpenseIntent.AmountChanged("25.0"))
        viewModel.dispatch(AddExpenseIntent.SaveExpense)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertTrue(fakeRepository.wasCalled)
    }

    @Test
    fun `NavigateBack emits NavigateToHome effect`() = runTest {
        viewModel.dispatch(AddExpenseIntent.NavigateBack)
        testDispatcher.scheduler.advanceUntilIdle()

        val effect = viewModel.effect.first()
        assertEquals(AddExpenseEffect.NavigateToHome, effect)
    }

    @Test
    fun `DismissError clears error`() = runTest {
        viewModel.dispatch(AddExpenseIntent.DismissError)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertNull(state.error)
    }

    private class FakeExpenseRepository : ExpenseRepository {
        var wasCalled = false
        val insertedExpenses = mutableListOf<ExpenseModel>()

        override fun getExpenses() = throw NotImplementedError()
        override suspend fun getExpenseById(id: String) = throw NotImplementedError()

        override suspend fun addExpense(expense: ExpenseModel): AppResult<ExpenseModel> {
            wasCalled = true
            insertedExpenses.add(expense)
            return AppResult.Success(expense)
        }

        override suspend fun updateExpense(expense: ExpenseModel) = throw NotImplementedError()
        override suspend fun deleteExpense(id: String) = throw NotImplementedError()
    }
}
