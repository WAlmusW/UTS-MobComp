package com.kel13.library.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

import com.kel13.library.data.Book

class BookViewModel : ViewModel() {
    val books = mutableStateListOf<Book>()

    // Add new book
    fun addBook(book: Book) {
        books.add(book)
    }

    // Update existing book
    fun updateBook(index: Int, book: Book) {
        books[index] = book
    }
}