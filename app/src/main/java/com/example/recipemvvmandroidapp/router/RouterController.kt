package com.example.recipemvvmandroidapp.router

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

class RouterController(
    val tabController: NavHostController,
    val modalController: NavHostController
) {
    fun navigateBetweenTabs(tabViewDestination: TabViewDestination){
        tabController.navigate(
            route = tabViewDestination.route,
            builder = {
                popUpTo = tabController.graph.startDestination
                launchSingleTop = true
            }
        )
    }

    fun navigateToRecipeDetailView(recipeId: Int){
        modalController.navigate(
            route = ViewDestination.RecipeDetailView.route + "/$recipeId"
        )
    }
}