package com.mobile.iexpense.core.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseCreator : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
