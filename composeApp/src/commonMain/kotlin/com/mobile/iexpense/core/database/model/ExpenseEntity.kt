package com.mobile.iexpense.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val amount: Double,
    val categoryKey: String,
    val date: Long,
    val note: String? = null
)