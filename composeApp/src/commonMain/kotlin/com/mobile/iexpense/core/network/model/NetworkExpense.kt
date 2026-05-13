package com.mobile.iexpense.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkExpense(
    val id: String,
    val title: String,
    val amount: Double,
    val category: String,
    val date: Long,
    val note: String? = null
)