package com.example.recipemvvmandroidapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.router.ViewDestination
import com.example.recipemvvmandroidapp.router.routerController
import com.example.recipemvvmandroidapp.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.view.tabView.SearchRecipeView
import com.example.recipemvvmandroidapp.view.viewComponent.BottomNavBar
import com.example.recipemvvmandroidapp.viewModel.HomeViewModel
import com.example.recipemvvmandroidapp.viewModel.homeViewModel

@Composable
fun Dependency.View.CreateHomeView(
    homeViewModel: HomeViewModel,
    navController: NavHostController
)
{
    val tabSelected = homeViewModel.selectedTab.value
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
            val routerController = viewModel.routerController()
            routerController.navController = navController
            NavHost(navController = navController, startDestination = TabViewDestination.Search.route)
            {
                TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
                    composable(tabViewDestination.route) {
                        homeViewModel.updateSelectedTab(tabViewDestination)
                        when(tabViewDestination)
                        {
                            TabViewDestination.Search -> SearchRecipeView()
                            TabViewDestination.Discovery -> DiscoveryView()
                        }
                    }
                }

                ViewDestination.values().map{ viewDestination: ViewDestination ->
                    composable(
                        route = viewDestination.route + "/{${viewDestination.arguments.first}}",
                        arguments = listOf(
                            navArgument(viewDestination.arguments.first){
                                type = viewDestination.arguments.second}
                        )
                    ) {
                        when(viewDestination)
                        {
                            ViewDestination.RecipeDetailView
                            -> RecipeDetailView(it.arguments?.getInt(viewDestination.arguments.first)!!)
                        }
                    }
                }
            }
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