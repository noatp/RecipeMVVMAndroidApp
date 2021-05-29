package com.example.recipemvvmandroidapp.ui.router

import androidx.navigation.NavHostController

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
}