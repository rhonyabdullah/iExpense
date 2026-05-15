package com.mobile.iexpense.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobile.iexpense.core.database.dao.ExpenseDao
import com.mobile.iexpense.core.database.model.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(AppDatabaseCreator::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
