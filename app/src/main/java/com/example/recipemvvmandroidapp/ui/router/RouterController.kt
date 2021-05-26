package com.example.recipemvvmandroidapp.ui.router

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.recipemvvmandroidapp.ui.view.RecipeDetailView
import com.example.recipemvvmandroidapp.ui.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.ui.view.tabView.SearchRecipeView
import com.example.recipemvvmandroidapp.ui.viewModel.DiscoveryViewModel
import com.example.recipemvvmandroidapp.ui.viewModel.RecipeDetailViewModel
import com.example.recipemvvmandroidapp.ui.viewModel.SearchRecipeViewModel

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
    fun AttachNavHostForTabController(
        modifier: Modifier = Modifier
    ){
        NavHost(
            navController = tabController,
            startDestination = TabViewDestination.Search.route,
            modifier = modifier
        )
        {
            TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
                composable(tabViewDestination.route) {
                    MapTabViewDestinationToComposable(tabViewDestination = tabViewDestination)
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

    @Composable
    fun MapTabViewDestinationToComposable(
        tabViewDestination: TabViewDestination,
    ){
        val router: RouterController = this
        when(tabViewDestination){
            TabViewDestination.Search -> SearchRecipeView(router = router)
            TabViewDestination.Discovery -> DiscoveryView(router = router)
        }
    }
}