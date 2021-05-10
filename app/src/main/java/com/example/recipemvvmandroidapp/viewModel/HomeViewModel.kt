package com.example.recipemvvmandroidapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.router.routerController

class HomeViewModel(
    routerController: RouterController
): ViewModel() {
    companion object{
        var instance: HomeViewModel? = null
    }
    private val _tabSelected = MutableLiveData(TabViewDestination.Search)
    val tabSelected: LiveData<TabViewDestination> = _tabSelected
    val onTabSelected: (TabViewDestination) -> Unit = {
        _tabSelected.value = it
        routerController.navigateBetweenTabs(it.route)
    }
}

fun Dependency.ViewModel.homeViewModel(): HomeViewModel{
    if(HomeViewModel.instance == null)
        HomeViewModel.instance = HomeViewModel(routerController = routerController())
    return HomeViewModel.instance!!
}