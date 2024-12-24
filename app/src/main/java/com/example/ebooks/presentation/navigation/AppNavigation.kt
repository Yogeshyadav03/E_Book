package com.example.ebooks.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ebooks.presentation.BooksByCategoryUi
import com.example.ebooks.presentation.PDFView
import com.example.ebooks.presentation.TabBar


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = routes.HomeScreen) {

        composable<routes.HomeScreen> {
          TabBar(navController)
        }

        composable<routes.BookByCategory> {
            //BooksByCategoryUi(navController)
            val data = it.toRoute<routes.BookByCategory>()
            BooksByCategoryUi(navController, category = data.category)
        }

        composable<routes.pdfViewer> {
            val data = it.toRoute<routes.pdfViewer>()
            PDFView(pdfUrl = data.pdfUrl)

        }



    }
    
}