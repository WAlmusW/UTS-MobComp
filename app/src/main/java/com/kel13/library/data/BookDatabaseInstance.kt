package com.kel13.library.data

import android.content.Context
import androidx.room.Room

object BookDatabaseInstance {
    @Volatile
    private var INSTANCE: BookDatabase? = null

    fun getDatabase(context: Context): BookDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java,
                "book_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}