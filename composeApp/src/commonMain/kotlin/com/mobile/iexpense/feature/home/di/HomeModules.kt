package com.mobile.iexpense.feature.home.di

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mobile.iexpense.core.domain.usecase.GetExpensesUseCase
import com.mobile.iexpense.feature.home.HomeEffectHandler
import com.mobile.iexpense.feature.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    factoryOf(::HomeEffectHandler)
}
