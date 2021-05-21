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
import androidx.navigation.compose.navArgument
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.router.ViewDestination
import com.example.recipemvvmandroidapp.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.view.tabView.SearchRecipeView
import com.example.recipemvvmandroidapp.view.viewComponent.BottomNavBar
import com.example.recipemvvmandroidapp.viewModel.DiscoveryViewModel
import com.example.recipemvvmandroidapp.viewModel.HomeViewModel
import com.example.recipemvvmandroidapp.viewModel.RecipeDetailViewModel
import com.example.recipemvvmandroidapp.viewModel.SearchRecipeViewModel

@Composable
fun CreateHomeView(
    tabSelected: TabViewDestination,
    router: RouterController,
    updateSelectedTab: (TabViewDestination) -> Unit,
)
{
    Scaffold (
        modifier = Modifier,
        bottomBar = { BottomAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(0.dp)
        ) {
            BottomNavBar(
                tabs = TabViewDestination.values(),
                tabSelected = tabSelected,
                onTabSelected = {router.navigateBetweenTabs(it)}
            )
        } },
        content = {
            NavHost(navController = router.tabController, startDestination = TabViewDestination.Search.route)
            {
                TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
                    composable(tabViewDestination.route) {
                        updateSelectedTab(tabViewDestination)
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
    val homeViewModel: HomeViewModel = viewModel()
    val tabSelected = homeViewModel.selectedTab.value
    CreateHomeView(
        tabSelected = tabSelected,
        router = router,
        updateSelectedTab = {tabViewDestination ->
            homeViewModel.updateSelectedTab(tabViewDestination)
        }
    )
}