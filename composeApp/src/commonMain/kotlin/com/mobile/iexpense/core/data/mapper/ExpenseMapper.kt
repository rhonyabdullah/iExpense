package com.mobile.iexpense.core.data.mapper

import com.mobile.iexpense.core.database.model.ExpenseEntity
import com.mobile.iexpense.core.domain.model.EntityCategory
import com.mobile.iexpense.core.domain.model.ExpenseModel
import com.mobile.iexpense.core.network.model.NetworkExpense

fun ExpenseEntity.toDomainModel(): ExpenseModel {
    return ExpenseModel(
        id = id,
        title = title,
        amount = amount,
        category = EntityCategory.ExpenseCategory.fromKey(categoryKey)
            ?: EntityCategory.ExpenseCategory.FOOD,
        date = date,
        note = note
    )
}

fun ExpenseModel.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        title = title,
        amount = amount,
        categoryKey = category.key,
        date = date,
        note = note
    )
}

fun NetworkExpense.toDomainModel(): ExpenseModel {
    return ExpenseModel(
        id = id,
        title = title,
        amount = amount,
        category = EntityCategory.ExpenseCategory.fromKey(category)
            ?: EntityCategory.ExpenseCategory.FOOD,
        date = date,
        note = note
    )
}

fun ExpenseModel.toNetworkModel(): NetworkExpense {
    return NetworkExpense(
        id = id,
        title = title,
        amount = amount,
        category = category.key,
        date = date,
        note = note
    )
}