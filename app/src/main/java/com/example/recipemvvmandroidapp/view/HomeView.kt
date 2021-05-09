package com.example.recipemvvmandroidapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.RouterContainer
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.view.viewComponent.BottomNavBar
import com.example.recipemvvmandroidapp.viewModel.HomeViewModel
import com.example.recipemvvmandroidapp.viewModel.homeViewModel

@Composable
fun Dependency.View.CreateHomeView(
    homeViewModel: HomeViewModel,
    navController: NavHostController
)
{
    val tabSelected: TabViewDestination by homeViewModel
        .tabSelected.observeAsState(TabViewDestination.Search)
    Scaffold (
        modifier = Modifier,
        topBar = {/*TODO*/},
        bottomBar = { BottomAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(0.dp)
        ) {
            BottomNavBar(
                titles = TabViewDestination.values().map { it.name },
                tabSelected = tabSelected,
                onTabSelected = homeViewModel.onTabSelected
            )
        } },
        content = {
            RouterContainer(navController = navController)
        }
    )
}

@Composable
fun Dependency.View.HomeView()
{
    val homeViewModel = viewModel.homeViewModel()
    val navController = rememberNavController()
    CreateHomeView(homeViewModel = homeViewModel, navController = navController)
}

@Preview
@Composable
fun PreviewHomeView()
{
    //HomeView()
}