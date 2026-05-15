package com.mobile.iexpense.feature.addexpense

import androidx.lifecycle.viewModelScope
import com.mobile.iexpense.core.common.effect.EffectChannel
import com.mobile.iexpense.core.common.effect.sendEffect
import com.mobile.iexpense.core.common.result.AppResult
import com.mobile.iexpense.core.common.viewmodel.BaseViewModel
import com.mobile.iexpense.core.domain.model.ExpenseModel
import com.mobile.iexpense.core.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

internal class AddExpenseViewModel(
    private val addExpenseUseCase: AddExpenseUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(AddExpenseState())
    val state: StateFlow<AddExpenseState> = _state.asStateFlow()

    private val _effect = EffectChannel<AddExpenseEffect>()
    val effect: Flow<AddExpenseEffect> = _effect.receiveAsFlow()

    init {
        dispatch(AddExpenseIntent.OnInit)
    }

    fun dispatch(intent: AddExpenseIntent) = when (intent) {
        AddExpenseIntent.OnInit -> onInit()
        is AddExpenseIntent.TitleChanged -> _state.reduce {
            it.copy(title = intent.title, titleError = false)
        }

        is AddExpenseIntent.AmountChanged -> _state.reduce {
            it.copy(amount = intent.amount, amountError = false)
        }

        is AddExpenseIntent.DateChanged -> _state.reduce {
            it.copy(date = intent.date, dateError = false)
        }

        is AddExpenseIntent.CategoryChanged -> _state.reduce {
            it.copy(category = intent.category)
        }

        is AddExpenseIntent.NotesChanged -> _state.reduce {
            it.copy(notes = intent.notes)
        }

        AddExpenseIntent.SaveExpense -> onSaveExpense()
        AddExpenseIntent.DismissError -> _state.reduce { it.copy(error = null) }
        AddExpenseIntent.NavigateBack -> _effect.sendEffect(AddExpenseEffect.NavigateToHome)
    }

    private fun onInit() {
        _state.reduce { it.copy(isLoading = false, error = null) }
    }

    private fun onSaveExpense() {
        val title = _state.value.title
        val amountStr = _state.value.amount
        val date = _state.value.date

        var hasError = false
        if (title.isBlank()) {
            _state.reduce { it.copy(titleError = true) }
            hasError = true
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _state.reduce { it.copy(amountError = true) }
            hasError = true
        }

        if (date <= 0) {
            _state.reduce { it.copy(dateError = true) }
            hasError = true
        }

        if (hasError) return

        _state.reduce { it.copy(isLoading = true) }

        viewModelScope.launch {
            val expense = ExpenseModel(
                id = Random.nextLong().toString(),
                title = title,
                amount = amount!!,
                category = _state.value.category,
                date = date,
                note = _state.value.notes.ifBlank { null }
            )

            when (val result = addExpenseUseCase(expense)) {
                is AppResult.Success -> {
                    _state.reduce { it.copy(isLoading = false) }
                    _effect.sendEffect(AddExpenseEffect.ExpenseSaved)
                    _effect.sendEffect(AddExpenseEffect.NavigateToHome)
                }

                is AppResult.Failure -> {
                    _state.reduce { it.copy(isLoading = false) }
                    _effect.sendEffect(AddExpenseEffect.ShowError(result.error.message))
                    handleFailure(result)
                }

                is AppResult.Loading -> { /* already handled */ }
            }
        }
    }

    private fun <T> MutableStateFlow<T>.reduce(reducer: (T) -> T) {
        this.value = reducer(this.value)
    }
}
