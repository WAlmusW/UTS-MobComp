package com.kel13.library.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

import com.kel13.library.data.Book
import com.kel13.library.data.DataSource
import com.kel13.library.ui.theme.BookAppTheme

@Composable
fun BookForm(
    book: Book? = null,
    onSave: (Book) -> Unit,
    onDelete: (() -> Unit)? = null,  // Optional onDelete function
    genres: List<String> = DataSource.genres
) {
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var publisher by remember { mutableStateOf(book?.publisher ?: "") }
    var year by remember { mutableStateOf(book?.year ?: "") }
    var genre by remember { mutableStateOf(book?.genre ?: genres[0]) }
    var summary by remember { mutableStateOf(book?.summary ?: "") }
    var expanded by remember { mutableStateOf(false) }

    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = publisher,
            onValueChange = { publisher = it },
            label = { Text("Publisher") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = year,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    year = newValue
                }
            },
            label = { Text("Year") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Gray) // Add a border
                .clickable { expanded = true }
                .padding(8.dp) // Add inner padding
        ) {
            Row {
                Text(
                    text = "Genre: $genre",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f) // Pushes the icon to the right
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Arrow"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genres.forEach { genreOption ->
                    DropdownMenuItem(
                        text = { Text(genreOption) },
                        onClick = {
                            genre = genreOption
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Summary") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSave(Book(title, author, publisher, year, genre, summary)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        if (onDelete != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun BookFormPreview() {
    BookAppTheme {
        BookForm(
            book = Book(
                title = "Sample Title",
                author = "Sample Author",
                publisher = "Sample Publisher",
                year = "2023",
                genre = "Fiction",
                summary = "This is a sample summary of the book."
            ),
            onSave = {},
            genres = listOf("Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Biography")
        )
    }
}