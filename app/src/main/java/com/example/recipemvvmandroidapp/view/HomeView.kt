package com.example.recipemvvmandroidapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.view.viewComponent.NavTabRow

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
            router.AttachNavHostForTabController()
        }
    )

    router.AttachNavHostForModalController()
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