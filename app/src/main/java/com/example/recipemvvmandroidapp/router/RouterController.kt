package com.example.recipemvvmandroidapp.router

import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.dependency.Dependency
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

class RouterController() {
    companion object{
        var instance: RouterController? = null
    }

    lateinit var navController: NavHostController

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

fun Dependency.Router.routerController(): RouterController{
    if(RouterController.instance == null)
    {
        RouterController.instance = RouterController()
    }
    return RouterController.instance!!
}