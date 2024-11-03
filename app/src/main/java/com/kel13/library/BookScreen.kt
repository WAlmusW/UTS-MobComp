package com.kel13.library

import RepeatingPatternBackground
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    val topBarModifier = when (currentRoute) {
        BookScreen.List.name -> {
            // Modifier for the first page with a cut corner shape and limited width
            Modifier
                .width(250.dp) // Adjust the width to cover half the screen
                .background(
                    color = Color(0xFF473535), // Brown color
                    shape = CutCornerShape(topEnd = 16.dp, bottomEnd = 16.dp) // Cut corner shape
                )
        }
        BookScreen.Create.name, BookScreen.Edit.name -> {
            // Modifier for the second and third pages with a transparent background
            Modifier.background(Color.Transparent)
        }
        else -> modifier
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = if (currentRoute == BookScreen.List.name) {
                    Color(0xFFA98A88) // Custom text color for the first page
                } else {
                    MaterialTheme.colorScheme.scrim // Default text color for other pages
                },
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = if (currentRoute == BookScreen.List.name) {
                Color.Transparent // Custom color for the first page
            } else {
                Color.Transparent // Transparent for the second and third pages
            }
        ),
        modifier = topBarModifier,
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
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_wallpaper), // Replace with your image resource
            contentDescription = null, // Background image does not need a description
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Adjust the scaling as needed
        )

        Scaffold(
            containerColor = Color.Transparent,
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
}
