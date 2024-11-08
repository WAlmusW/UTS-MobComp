package com.kel13.library.ui

import RepeatingPatternBackground
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kel13.library.R

import com.kel13.library.data.Book
import com.kel13.library.ui.theme.BookAppTheme

@Composable
fun BookListScreen(
    viewModel: BookViewModel = viewModel(),
    onEditBook: (Int) -> Unit,
    onAddBook: () -> Unit,
    books: List<Book> = viewModel.books
) {
    var searchQuery by remember { mutableStateOf("") }
    var editButtonsVisible by remember { mutableStateOf(false) }

    // Filter the books based on the search query
    val filteredBooks = books.filter { it.title.contains(searchQuery, ignoreCase = true) }

    RepeatingPatternBackground()

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                FloatingActionButton(
                    onClick = { editButtonsVisible = !editButtonsVisible },
                    shape = RoundedCornerShape(50),
                    content = {
                        Image(painter = painterResource(id = R.drawable.ic_edit_book), contentDescription = "Edit")
                    }
                )
                FloatingActionButton(
                    onClick = onAddBook,
                    shape = RoundedCornerShape(50),
                    content = {
                        Image(painter = painterResource(id = R.drawable.ic_new_book), contentDescription = "Add Book")
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(16.dp)
                .width(3000.dp)
                .height(675.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFF5F5DC))
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Box(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                    Row {
                        Text(
                            text = "Books:",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search title") },
                            shape = RoundedCornerShape(20.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 70.dp)
                ) {
                    items(filteredBooks.size) { index ->
                        BookItem(
                            book = filteredBooks[index],
                            onEditClick = { onEditBook(index) },
                            showEditButton = editButtonsVisible
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onEditClick: () -> Unit, showEditButton: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(0xFF7A3838), // Brown color
                shape = CutCornerShape(topEnd = 24.dp, bottomStart = 24.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            if (showEditButton) {
                Button(
                    onClick = onEditClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF57878D))) {
                    Text("Edit")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    BookAppTheme {
        RepeatingPatternBackground()

        BookListScreen(
            onEditBook = {},
            onAddBook = {},
        )
    }
}