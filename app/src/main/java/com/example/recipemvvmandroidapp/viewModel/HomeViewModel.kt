package com.example.recipemvvmandroidapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipemvvmandroidapp.router.TabViewDestination
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
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
