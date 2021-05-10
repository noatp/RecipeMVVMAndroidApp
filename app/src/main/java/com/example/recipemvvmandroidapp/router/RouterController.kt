package com.example.recipemvvmandroidapp.router

import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.dependency.Dependency
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

class RouterController: ViewModel() {
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
}

fun Dependency.ViewModel.routerController(): RouterController{
    if(RouterController.instance == null)
    {
        RouterController.instance = RouterController()
    }
    return RouterController.instance!!
}