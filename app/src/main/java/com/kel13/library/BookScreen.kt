package com.kel13.library

import RepeatingPatternBackground
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.kel13.library.ui.BookListScreen
import com.kel13.library.ui.BookViewModel
import com.kel13.library.ui.BookForm

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
    currentRoute: String?, // Add currentRoute parameter
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (currentRoute == BookScreen.List.name) {
                // Only apply the background with CutCornerShape on the List page
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF473535), // Set the brown color
                            shape = CutCornerShape(topEnd = 16.dp, bottomEnd = 16.dp) // Customize the corner
                        )
                        .padding(horizontal = 20.dp, vertical = 8.dp) // Inner padding for the text
                ) {
                    Text(
                        text = title,
                        color = Color(0xFFA98A88), // Set text color to make it stand out
                        style = MaterialTheme.typography.titleLarge // Adjust text style as needed
                    )
                }
            } else {
                // Simple text without background for other pages
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimaryContainer, // Use default text color
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
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
    viewModel: BookViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    RepeatingPatternBackground()

    Scaffold(
        topBar = {
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentRoute = currentBackStackEntry?.destination?.route
            val canNavigateBack = navController.previousBackStackEntry != null
            val title = when {
                currentRoute == BookScreen.List.name -> "MuArchive"
                currentRoute == BookScreen.Create.name -> "Add New Book"
                currentRoute?.startsWith(BookScreen.Edit.name) == true -> "Edit Book"
                else -> "MuArchive"
            }

            BookAppBar(
                title = title,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                currentRoute = currentRoute
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
                val book = index?.let { viewModel.books.getOrNull(it) }

                if (book != null) {
                    BookForm(
                        book = book,
                        onSave = { updatedBook ->
                            viewModel.updateBook(index, updatedBook)
                            navController.popBackStack()
                        },
                        onDelete = {
                            viewModel.deleteBook(index)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
