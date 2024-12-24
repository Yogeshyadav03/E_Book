package com.example.ebooks.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ebooks.ViewModel.AppViewModel
import com.example.ebooks.presentation.navigation.routes


@Composable
fun CategoryScreenUi(navController: NavController,viewModel: AppViewModel = hiltViewModel()) {

    val state = viewModel.getAllCategoryState.collectAsState()
    val data = state.value.data ?: emptyList()

    println("Data to display: $data")

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllCategory()
    }

    when {
        state.value.isLoading -> {
            Box(modifier =  Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.data.isNotEmpty() -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(data) { category ->
                    Category(
                        name = category.name,
                        url = category.categoryImageUrl,
                        navController = navController
                    )


                }
            }
        }
        state.value.error != null -> {
            Text("Error: ${state.value.error}")
        }
    }
}

@Composable
fun Category(
    name: String,
    url: String,
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavController
){
    Card(modifier = Modifier
           .fillMaxWidth()
           .padding(8.dp)
        .clickable {
            navController.navigate(routes.BookByCategory(category = name))
        }
    ){
        Column(modifier = Modifier
            .fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = name,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
            }

        }

    }

}