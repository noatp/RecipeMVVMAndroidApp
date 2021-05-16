package com.example.recipemvvmandroidapp.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.TabViewDestination
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class HomeViewModel(): ViewModel() {
    var selectedTab: MutableState<TabViewDestination> = mutableStateOf(TabViewDestination.Search)

    fun updateSelectedTab(tabViewDestination: TabViewDestination){
        selectedTab.value = tabViewDestination
    }
}

class HomeViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel()  as T
    }
}

@Composable
fun Dependency.ViewModel.homeViewModel(): HomeViewModel{
    return viewModel(
        key = "HomeViewModel",
        factory = HomeViewModelFactory()
    )
}