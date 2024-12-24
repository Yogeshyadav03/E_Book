package com.example.ebooks.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.ebooks.ConnectionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBar(navController: NavController) {

    val tabs = listOf(
        Tabitem("Category", Icons.Rounded.Category),
        Tabitem("Books", Icons.Rounded.MenuBook)
    )

    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val scope = rememberCoroutineScope()

    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize())
    {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tabitem ->
                Tab(
                    selected = pagerState.currentPage == index,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                      text = {
                          Text(text = tabitem.name)
                      },
                      icon = {
                          Icon(imageVector = tabitem.icon, contentDescription = null)
                      }
                )
            }
        }

        HorizontalPager(state = pagerState)
        {
            if (ConnectionManager().checkConnection(context)) {
                when (it) {
                    0 -> CategoryScreenUi(navController = navController)
                    1 -> AllBooksScreenUi(navController = navController)
                }
            }else{
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Rounded.WifiOff, contentDescription = null, modifier = Modifier.size(100.dp))
                    Text(text = "No Internet Connection")
                }
            }
        }
    }
    
}

data class Tabitem(
    val name : String,
    val icon : ImageVector,

    )