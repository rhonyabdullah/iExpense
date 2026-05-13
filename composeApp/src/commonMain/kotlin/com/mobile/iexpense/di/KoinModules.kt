package com.mobile.iexpense.di

import com.mobile.iexpense.feature.home.di.homeModule
import org.koin.dsl.module

val sharedModule = module {
    includes(homeModule)
}
