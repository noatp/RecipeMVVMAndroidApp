package com.example.recipemvvmandroidapp.ui.router

import androidx.navigation.NavHostController

class RouterController(
    val tabController: NavHostController,
    val modalController: NavHostController
) {
    fun navigateBetweenTabs(tabViewDestination: TabViewDestination){
        tabController.navigate(
            route = tabViewDestination.route,
            builder = {
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