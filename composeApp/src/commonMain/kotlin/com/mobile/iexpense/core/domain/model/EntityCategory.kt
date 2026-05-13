package com.mobile.iexpense.core.domain.model

sealed interface EntityCategory {
    val key: String

    enum class ExpenseCategory(override val key: String) : EntityCategory {
        FOOD("food"),
        TRANSPORT("transport"),
        UTILITIES("utilities"),
        ENTERTAINMENT("entertainment"),
        HEALTH("health"),
        SHOPPING("shopping");

        companion object {
            fun fromKey(key: String): ExpenseCategory? =
                entries.find { it.key == key }
        }
    }

    enum class IncomeCategory(override val key: String) : EntityCategory {
        SALARY("salary"),
        INVESTMENT("investment"),
        FREELANCE("freelance");

        companion object {
            fun fromKey(key: String): IncomeCategory? =
                entries.find { it.key == key }
        }
    }
}