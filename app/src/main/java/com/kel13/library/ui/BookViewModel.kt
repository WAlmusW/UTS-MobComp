package com.kel13.library.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.kel13.library.data.Book
import com.kel13.library.data.BookDao
import com.kel13.library.data.BookDatabaseInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val bookDao: BookDao = BookDatabaseInstance.getDatabase(application).bookDao()
    val books = mutableStateListOf<Book>()

    init {
        loadBooks()
    }

    // Load books from the database
    private fun loadBooks() {
        viewModelScope.launch {
            val bookList = withContext(Dispatchers.IO) { bookDao.getAllBooks() }
            // Perform UI updates on the main thread
            books.addAll(bookList)
        }
    }

    // Add new book
    fun addBook(book: Book) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookDao.insertBook(book)
            }
            // Update UI state on the main thread
            books.add(book)
        }
    }

    // Update existing book
    fun updateBook(index: Int, book: Book) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookDao.updateBook(book)
            }
            // Update UI state on the main thread
            books[index] = book
        }
    }

    // Delete book by index
    fun deleteBook(index: Int) {
        if (index in books.indices) {
            val book = books[index]
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    bookDao.deleteBook(book)
                }
                // Update UI state on the main thread
                books.removeAt(index)
            }
        }
    }

    // Alternatively, delete book by instance
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookDao.deleteBook(book)
            }
            // Update UI state on the main thread
            books.remove(book)
        }
    }
}