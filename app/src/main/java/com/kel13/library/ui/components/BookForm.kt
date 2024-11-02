package com.kel13.library.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.kel13.library.data.Book
import com.kel13.library.data.DataSource

@Composable
fun BookForm(
    book: Book? = null,
    onSave: (Book) -> Unit,
    genres: List<String> = DataSource.genres
) {
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var publisher by remember { mutableStateOf(book?.publisher ?: "") }
    var year by remember { mutableStateOf(book?.year ?: "") }
    var genre by remember { mutableStateOf(book?.genre ?: genres[0]) }
    var summary by remember { mutableStateOf(book?.summary ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = author, onValueChange = { author = it }, label = { Text("Author") })
        TextField(value = publisher, onValueChange = { publisher = it }, label = { Text("Publisher") })
        TextField(value = year, onValueChange = { year = it }, label = { Text("Year") })

        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text(text = "Genre: $genre", modifier = Modifier.clickable { expanded = true })
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                genres.forEach { genreOption ->
                    DropdownMenuItem(text={ Text(genreOption) }, onClick = { genre = genreOption; expanded = false })
                }
            }
        }

        TextField(value = summary, onValueChange = { summary = it }, label = { Text("Summary") }, modifier = Modifier.height(150.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onSave(Book(title, author, publisher, year, genre, summary))
        }) {
            Text("Save")
        }
    }
}

