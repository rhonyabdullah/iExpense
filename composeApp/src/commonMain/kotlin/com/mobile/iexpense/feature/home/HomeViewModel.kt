package com.mobile.iexpense.feature.home

import androidx.lifecycle.viewModelScope
import com.mobile.iexpense.core.common.effect.EffectChannel
import com.mobile.iexpense.core.common.effect.sendEffect
import com.mobile.iexpense.core.common.result.handle
import com.mobile.iexpense.core.common.viewmodel.BaseViewModel
import com.mobile.iexpense.core.domain.usecase.GetExpensesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

internal class HomeViewModel(
    private val getExpensesUseCase: GetExpensesUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = EffectChannel<HomeEffect>()
    val effect: Flow<HomeEffect> = _effect.receiveAsFlow()

    init {
        dispatch(HomeIntent.OnInit)
    }

    override fun onFailure(error: Throwable) {
        _state.reduce { it.copy(isLoading = false) }
        _effect.sendEffect(HomeEffect.ShowError(error.message))
    }

    fun dispatch(intent: HomeIntent) = when (intent) {
        HomeIntent.OnInit -> onInit()
        HomeIntent.OnAddExpenseClick -> onAddExpenseClick()
        HomeIntent.OnSearchClick -> onSearchClick()
    }

    private fun onInit() {
        _state.reduce { it.copy(isLoading = true, expenses = emptyList(), totalThisMonth = 0.0) }

        viewModelScope.launch {
            getExpensesUseCase().handle {
                onLoading { _state.reduce { it.copy(isLoading = true) } }
                onSuccess { expenses ->
                    val total = expenses.sumOf { it.amount }
                    val uiExpenses = expenses.map { model ->
                        ExpenseUi(
                            id = model.id,
                            title = model.title,
                            amount = model.amount,
                            category = model.category,
                            date = formatExpenseDate(model.date)
                        )
                    }
                    _state.reduce {
                        it.copy(
                            isLoading = false,
                            expenses = uiExpenses,
                            totalThisMonth = total
                        )
                    }
                }
                onFailure(::handleFailure)
            }
        }
    }

    private fun onAddExpenseClick() {
        _effect.sendEffect(HomeEffect.NavigateToAddExpense)
    }

    private fun onSearchClick() {
        _effect.sendEffect(HomeEffect.NavigateToSearch)
    }

    private fun formatExpenseDate(timestamp: Long): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val expenseDate = Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val daysDifference = now.toEpochDays() - expenseDate.toEpochDays()

        return when (daysDifference) {
            0L -> "Today"
            1L -> "Yesterday"
            else -> "${expenseDate.day.toString().padStart(2, '0')}/" +
                    "${expenseDate.month.number.toString().padStart(2, '0')}/${expenseDate.year}"
        }
    }

    private fun <T> MutableStateFlow<T>.reduce(reducer: (T) -> T) {
        this.value = reducer(this.value)
    }
}
