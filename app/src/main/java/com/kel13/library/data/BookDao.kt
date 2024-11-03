package com.kel13.library.data

import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): List<Book>

    @Insert
    fun insertBook(book: Book)

    @Update
    fun updateBook(book: Book)

    @Delete
    fun deleteBook(book: Book)
}
