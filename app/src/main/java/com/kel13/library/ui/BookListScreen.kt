package com.kel13.library.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.kel13.library.data.Book
import com.kel13.library.ui.theme.BookAppTheme

@Composable
fun BookListScreen(
    viewModel: BookViewModel = viewModel(),
    onEditBook: (Int) -> Unit,
    onAddBook: () -> Unit,
    books: List<Book> = viewModel.books) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(70.dp),
                onClick = onAddBook) {
                Text("Add Book")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(books.size) { index ->
                    BookItem(
                        book = books[index],
                        onEditClick = { onEditBook(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onEditClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = book.title, style = MaterialTheme.typography.titleLarge)
            Text(text = book.author, style = MaterialTheme.typography.titleSmall)
        }
        Button(onClick = onEditClick) {
            Text("Edit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    BookAppTheme {
        BookListScreen(
            onEditBook = {},
            onAddBook = {},
            books = listOf(
                Book("Book One", "Author One", "Publisher One", "2022", "Fiction", "Summary of Book One."),
                Book("Book Two", "Author Two", "Publisher Two", "2023", "Science", "Summary of Book Two.")
            )
        )
    }
}
