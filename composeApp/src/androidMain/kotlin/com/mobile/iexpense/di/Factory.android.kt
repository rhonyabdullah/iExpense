package com.mobile.iexpense.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobile.iexpense.core.database.AppDatabase

actual class Factory(private val context: Context) {

    actual val databaseBuilder: RoomDatabase.Builder<AppDatabase>
        get() {
            val dbFile = context.getDatabasePath(DATABASE_FILE_NAME)
            return Room.databaseBuilder<AppDatabase>(
                context = context,
                name = dbFile.absolutePath,
            )
        }

    actual fun withPath(fileName: String): String =
        context.filesDir.resolve(fileName).absolutePath
}
