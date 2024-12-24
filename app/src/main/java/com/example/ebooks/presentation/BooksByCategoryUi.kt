package com.example.ebooks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebooks.ViewModel.AppViewModel

@Composable
fun BooksByCategoryUi(navController: NavController,viewModel: AppViewModel = hiltViewModel(), category : String)  {

    val state = viewModel.getBooksByCategoryState.collectAsState()
    val data = state.value.data ?: emptyList()

    LaunchedEffect(key1 = Unit){
        viewModel.getBooksByCategory(category)
    }


    when{
        state.value.isLoading -> {
            Box(modifier =  Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }
        state.value.data.isNotEmpty() -> {
            LazyColumn {
                items(data){
                    books(
                        title = it.BookName,
                        url = it.bookUrl,
                        image = it.bookImage,
                        author = it.bookAuthor,
                        navController = navController
                    )
                }

            }

        }
        state.value.error != null -> {
            Box(modifier =  Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.value.error}")
            }

        }

    }
    
}