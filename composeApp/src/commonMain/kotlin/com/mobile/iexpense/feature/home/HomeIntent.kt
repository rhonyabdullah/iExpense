package com.mobile.iexpense.feature.home

sealed interface HomeIntent {
    data object OnInit : HomeIntent
    data object OnAddExpenseClick : HomeIntent
    data object OnSearchClick : HomeIntent
}