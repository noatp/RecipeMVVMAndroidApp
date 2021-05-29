package com.example.recipemvvmandroidapp.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.router.TabViewDestination
import com.example.recipemvvmandroidapp.ui.theme.RecipeMVVMTheme
import com.example.recipemvvmandroidapp.ui.view.viewComponent.NavTabRow

@Composable
fun CreateHomeView(
    tabs: List<TabViewDestination>,
    currentTab: TabViewDestination,
    router: RouterController,
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
                router.AttachNavHostForTabController(modifier = Modifier.padding(it))
            }
        )

        router.AttachNavHostForModalController()
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