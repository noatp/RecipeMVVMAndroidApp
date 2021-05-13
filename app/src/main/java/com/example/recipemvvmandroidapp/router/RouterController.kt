package com.example.recipemvvmandroidapp.router

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

class RouterController(
    val navController: NavHostController
) {
    fun navigateBetweenTabs(tabViewDestination: TabViewDestination){
        navController.navigate(
            route = tabViewDestination.route,
            builder = {
                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }
        )
    }

    fun navigateToRecipeDetailView(recipeId: Int){
        navController.navigate(
            route = ViewDestination.RecipeDetailView.route + "/$recipeId"
        )
    }
}