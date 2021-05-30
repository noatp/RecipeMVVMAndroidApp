package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import com.example.recipemvvmandroidapp.domain.useCase.getRecipeListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DiscoveryViewModel(
    getRecipeListUseCase: GetRecipeListUseCase
): ViewModel(){

    var pagingFlow: MutableState<Flow<PagingData<RecipeDTO>>> = mutableStateOf(flowOf(PagingData.empty()))

    init{
        when(val useCaseResult = getRecipeListUseCase.execute(query = "a"))
        {
            is UseCaseResult.Success -> {
                pagingFlow.value = useCaseResult.resultValue.cachedIn(viewModelScope)
            }
            is UseCaseResult.Error -> Log.d("Debug: DiscoveryViewModel",
                useCaseResult.exception.toString()
            )
        }
    }
}

class DiscoveryViewModelFactory(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T {
        return DiscoveryViewModel(getRecipeListUseCase) as T
    }
}

@Composable
fun Dependency.ViewModel.discoveryViewModel(): DiscoveryViewModel {
    return viewModel(
        key = "DiscoveryViewModel",
        factory = DiscoveryViewModelFactory(
            useCase.getRecipeListUseCase()
        )
    )
}