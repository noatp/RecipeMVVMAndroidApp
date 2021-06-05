package com.example.recipemvvmandroidapp.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.router.TabViewDestination
import com.example.recipemvvmandroidapp.ui.router.ViewDestination
import com.example.recipemvvmandroidapp.ui.theme.RecipeMVVMTheme
import com.example.recipemvvmandroidapp.ui.view.tabView.DiscoveryView
import com.example.recipemvvmandroidapp.ui.view.tabView.SearchRecipeView
import com.example.recipemvvmandroidapp.ui.view.viewComponent.NavTabRow

@ExperimentalMaterialApi
@Composable
fun HomeView(
    router: RouterController,
    tabs: List<TabViewDestination>,
    currentTab: TabViewDestination,
    view: Dependency.View
)
{
    RecipeMVVMTheme {
        Scaffold (
            modifier = Modifier,
            bottomBar = { BottomAppBar {
                NavTabRow(
                    tabs = tabs,
                    currentTab = currentTab,
                    onTabSelected = {router.navigateBetweenTabs(it)}
                )
            } },
            content = {
                NavHost(
                    navController = router.tabController,
                    startDestination = TabViewDestination.Search.route,
                    modifier = Modifier.padding(it)
                )
                {
                    TabViewDestination.values().map { tabViewDestination: TabViewDestination ->
                        composable(tabViewDestination.route) {
                            when(tabViewDestination)
                            {
                                TabViewDestination.Search -> view.SearchRecipeView(router)
                                TabViewDestination.Discovery -> view.DiscoveryView(router)
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
                        ViewDestination.RecipeDetailView
                        -> view.RecipeDetailView(it.arguments?.getInt(viewDestination.arguments.first)!!)
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Dependency.View.HomeView(router: RouterController)
{
    val tabBackStackEntry = router.tabController.currentBackStackEntryAsState()
    val tabs = TabViewDestination.values().toList()
    val currentTab = TabViewDestination.getTabFromRoute(tabBackStackEntry.value?.destination?.route)
    HomeView(
        router = router,
        tabs = tabs,
        currentTab = currentTab,
        view = this
    )
}