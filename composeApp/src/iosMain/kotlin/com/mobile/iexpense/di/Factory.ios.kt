package com.mobile.iexpense.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobile.iexpense.core.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class Factory {

    actual val databaseBuilder: RoomDatabase.Builder<AppDatabase>
        get() = Room.databaseBuilder<AppDatabase>(withPath(DATABASE_FILE_NAME))

    actual fun withPath(fileName: String): String = "${documentDirectory()}/$fileName"

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
