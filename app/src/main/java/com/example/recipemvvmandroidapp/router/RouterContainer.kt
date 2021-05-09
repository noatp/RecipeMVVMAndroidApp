package com.example.recipemvvmandroidapp.router

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.view.RecipeDetailView
import com.example.recipemvvmandroidapp.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.view.tabView.SearchRecipeView

@Composable
fun Dependency.View.RouterContainer(
    navController: NavHostController
)
{
    val routerController = viewModel.routerController()
    routerController.navController = navController
    NavHost(navController = navController, startDestination = TabViewDestination.Search.route)
    {
        TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
            composable(tabViewDestination.route) {
                when(tabViewDestination)
                {
                    TabViewDestination.Search -> SearchRecipeView()
                    TabViewDestination.Discovery -> DiscoveryView()
                }
            }
        }

        ViewDestination.values().map{ viewDestination: ViewDestination ->
            composable(viewDestination.route) {
                when(viewDestination)
                {
                    ViewDestination.RecipeDetailView -> RecipeDetailView()
                }
            }
        }

    }
}