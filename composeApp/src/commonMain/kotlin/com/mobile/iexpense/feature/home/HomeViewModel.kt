package com.mobile.iexpense.feature.home

import com.mobile.iexpense.core.common.effect.EffectChannel
import com.mobile.iexpense.core.common.effect.sendEffect
import com.mobile.iexpense.core.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

internal class HomeViewModel : BaseViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = EffectChannel<HomeEffect>()
    val effect: Flow<HomeEffect> = _effect.receiveAsFlow()

    init {
        dispatch(HomeIntent.OnInit)
    }

    fun dispatch(intent: HomeIntent) = when (intent) {
        HomeIntent.OnInit -> onInit()
        HomeIntent.OnAddExpenseClick -> onAddExpenseClick()
        HomeIntent.OnSearchClick -> onSearchClick()
    }

    private fun onInit() {
        _state.value = _state.value.copy(isLoading = false, expenses = emptyList())
    }

    private fun onAddExpenseClick() {
        _effect.sendEffect(HomeEffect.NavigateToAddExpense)
    }

    private fun onSearchClick() {
        _effect.sendEffect(HomeEffect.NavigateToSearch)
    }
}
