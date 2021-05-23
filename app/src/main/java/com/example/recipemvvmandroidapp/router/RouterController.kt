package com.example.recipemvvmandroidapp.router

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.recipemvvmandroidapp.view.RecipeDetailView
import com.example.recipemvvmandroidapp.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.view.tabView.SearchRecipeView
import com.example.recipemvvmandroidapp.viewModel.DiscoveryViewModel
import com.example.recipemvvmandroidapp.viewModel.RecipeDetailViewModel
import com.example.recipemvvmandroidapp.viewModel.SearchRecipeViewModel

class RouterController(
    val tabController: NavHostController,
    val modalController: NavHostController
) {
    var isModalPresented = false

    fun popOffModal(): Boolean{
        if (isModalPresented){
            modalController.popBackStack()
            isModalPresented = false
            return true
        }
        return false
    }

    fun navigateBetweenTabs(tabViewDestination: TabViewDestination){
        tabController.navigate(
            route = tabViewDestination.route,
            builder = {
                popUpTo(tabController.graph.startDestinationId)
                launchSingleTop = true
            }
        )
    }

    fun navigateToRecipeDetailView(recipeId: Int){
        isModalPresented = true
        modalController.navigate(
            route = ViewDestination.RecipeDetailView.route + "/$recipeId"
        )
    }

    @Composable
    fun AttachNavHostForTabController(){
        NavHost(navController = tabController, startDestination = TabViewDestination.Search.route)
        {
            TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
                composable(tabViewDestination.route) {
                    when(tabViewDestination)
                    {
                        TabViewDestination.Search -> {
                            val viewModel = hiltViewModel<SearchRecipeViewModel>(backStackEntry = it)
                            SearchRecipeView(this@RouterController, viewModel)
                        }
                        TabViewDestination.Discovery -> {
                            val viewModel = hiltViewModel<DiscoveryViewModel>(backStackEntry = it)
                            DiscoveryView(this@RouterController, viewModel)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AttachNavHostForModalController(){
        NavHost(navController = modalController, startDestination = "blank")
        {
            composable(route = "blank") {}
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
                        ViewDestination.RecipeDetailView -> {
                            val viewModel = hiltViewModel<RecipeDetailViewModel>(backStackEntry = it)
                            RecipeDetailView(
                                recipeId = it.arguments?.getInt(viewDestination.arguments.first)!!,
                                recipeDetailViewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}