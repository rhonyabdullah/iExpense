package com.mobile.iexpense.di

import com.mobile.iexpense.core.data.repository.ExpenseRepositoryImpl
import com.mobile.iexpense.core.database.AppDatabase
import com.mobile.iexpense.core.database.source.ExpenseDatabaseDataSource
import com.mobile.iexpense.core.domain.repository.ExpenseRepository
import com.mobile.iexpense.core.domain.usecase.AddExpenseUseCase
import com.mobile.iexpense.core.domain.usecase.DeleteExpenseUseCase
import com.mobile.iexpense.core.domain.usecase.GetExpenseByIdUseCase
import com.mobile.iexpense.core.domain.usecase.GetExpensesUseCase
import com.mobile.iexpense.core.domain.usecase.UpdateExpenseUseCase
import com.mobile.iexpense.core.network.source.ExpenseNetworkDataSource
import org.koin.dsl.KoinAppDeclaration
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import com.mobile.iexpense.feature.addexpense.di.addExpenseModule
import com.mobile.iexpense.feature.home.di.homeModule
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            appModule,
            networkModule,
            databaseModule,
            repositoryModule,
            useCaseModule,
            presentationModule,
            homeModule,
            addExpenseModule,
        )
    }
}

expect val platformModule: Module

private val appModule: Module
    get() = module {
        single {
            createAppDatabase(builder = get<Factory>().databaseBuilder)
        }
    }

val networkModule = module {
    singleOf(::ExpenseNetworkDataSource)
}

val databaseModule = module {
    single { get<AppDatabase>().expenseDao() }
    singleOf(::ExpenseDatabaseDataSource)
}

val repositoryModule = module {
    singleOf(::ExpenseRepositoryImpl) bind ExpenseRepository::class
}

val useCaseModule = module {
    singleOf(::GetExpensesUseCase)
    singleOf(::GetExpenseByIdUseCase)
    singleOf(::AddExpenseUseCase)
    singleOf(::UpdateExpenseUseCase)
    singleOf(::DeleteExpenseUseCase)
}

val presentationModule = module {
    // Effect handlers registered as factory in feature modules
}

val sharedModule = module {
    includes(
        platformModule,
        appModule,
        networkModule,
        databaseModule,
        repositoryModule,
        useCaseModule,
        presentationModule,
        homeModule,
        addExpenseModule,
    )
}
