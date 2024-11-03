package com.kel13.library.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val publisher: String,
    val year: String,
    val genre: String,
    val summary: String
)

//data class Book(
//    val title: String,
//    val author: String,
//    val publisher: String,
//    val year: String,
//    val genre: String,
//    val summary: String
//)
