package com.example.recipemvvmandroidapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.router.ViewDestination
import com.example.recipemvvmandroidapp.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.view.tabView.SearchRecipeView
import com.example.recipemvvmandroidapp.view.viewComponent.NavTabRow
import com.example.recipemvvmandroidapp.viewModel.DiscoveryViewModel
import com.example.recipemvvmandroidapp.viewModel.RecipeDetailViewModel
import com.example.recipemvvmandroidapp.viewModel.SearchRecipeViewModel

@Composable
fun CreateHomeView(
    tabs: List<TabViewDestination>,
    currentTab: TabViewDestination,
    router: RouterController,
)
{
    Scaffold (
        modifier = Modifier,
        bottomBar = { BottomAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(0.dp)
        ) {
            NavTabRow(
                tabs = tabs,
                currentTab = currentTab,
                onTabSelected = {router.navigateBetweenTabs(it)}
            )
        } },
        content = {
            NavHost(navController = router.tabController, startDestination = TabViewDestination.Search.route)
            {
                TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
                    composable(tabViewDestination.route) {
                        when(tabViewDestination)
                        {
                            TabViewDestination.Search -> {
                                val viewModel = hiltViewModel<SearchRecipeViewModel>(backStackEntry = it)
                                SearchRecipeView(router, viewModel)
                            }
                            TabViewDestination.Discovery -> {
                                val viewModel = hiltViewModel<DiscoveryViewModel>(backStackEntry = it)
                                DiscoveryView(router, viewModel)
                            }
                        }
                    }
                }
            }
        }
    )

    NavHost(navController = router.modalController, startDestination = "blank")
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
fun HomeView(router: RouterController)
{
    val tabBackStackEntry = router.tabController.currentBackStackEntryAsState()
    val tabs = TabViewDestination.values().toList()
    val currentTab = TabViewDestination.getTabFromRoute(tabBackStackEntry.value?.destination?.route)
    CreateHomeView(
        currentTab = currentTab,
        router = router,
        tabs = tabs
    )
}