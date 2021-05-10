package com.example.recipemvvmandroidapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.router.routerController
import androidx.compose.runtime.MutableState

class HomeViewModel(
    routerController: RouterController
): ViewModel() {
    companion object{
        var instance: HomeViewModel? = null
    }

    var selectedTab: MutableState<TabViewDestination> = mutableStateOf(TabViewDestination.Search)

    fun updateSelectedTab(tabViewDestination: TabViewDestination){
        selectedTab.value = tabViewDestination
    }

    val onTabSelected: (TabViewDestination) -> Unit = {
        routerController.navigateBetweenTabs(it)
    }
}

fun Dependency.ViewModel.homeViewModel(): HomeViewModel{
    if(HomeViewModel.instance == null)
        HomeViewModel.instance = HomeViewModel(routerController = routerController())
    return HomeViewModel.instance!!
}