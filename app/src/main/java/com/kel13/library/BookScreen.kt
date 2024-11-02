package com.kel13.library

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.kel13.library.ui.BookListScreen
import com.kel13.library.ui.BookViewModel
import com.kel13.library.ui.components.BookForm

enum class BookScreen {
    List,
    Create,
    Edit
}

@Composable
fun BookAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun BookApp(
    viewModel: BookViewModel =  viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val canNavigateBack = navController.previousBackStackEntry != null
            val title = when (currentBackStackEntry?.destination?.route) {
                BookScreen.List.name -> "My Books"
                BookScreen.Create.name -> "Add New Book"
                BookScreen.Edit.name -> "Edit Book"
                else -> "Book App"
            }

            BookAppBar(
                title = title,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookScreen.List.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Main list of books
            composable(route = BookScreen.List.name) {
                BookListScreen(
                    viewModel = viewModel,
                    onEditBook = { index -> navController.navigate("${BookScreen.Edit.name}/$index") },
                    onAddBook = { navController.navigate(BookScreen.Create.name) }
                )
            }

            // Create book screen
            composable(route = BookScreen.Create.name) {
                BookForm(
                    onSave = { book ->
                        viewModel.addBook(book)
                        navController.popBackStack()
                    }
                )
            }

            // Edit book screen
            composable(route = "${BookScreen.Edit.name}/{index}") { backStackEntry ->
                val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                val book = index?.let { viewModel.books[it] }

                if (book != null) {
                    BookForm(
                        book = book,
                        onSave = { updatedBook ->
                            viewModel.updateBook(index, updatedBook)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}