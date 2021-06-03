package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import com.example.recipemvvmandroidapp.domain.useCase.getRecipeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {
//    //data for search bar
//    val searchBarText: MutableState<String> = mutableStateOf("")
//    //event for search bar
//    val onSearchTextChanged: (String) -> Unit = {
//        searchBarText.value = it
//    }
//
//    //data for lazy list
//    var pagingFlow: MutableState<Flow<PagingData<RecipeDTO>>> = mutableStateOf(flowOf(PagingData.empty()))
//
//    val onSearch: () -> Unit = {
//        viewModelScope.launch(Dispatchers.IO) {
//            when(val useCaseResult = getRecipeListUseCase.execute(query = searchBarText.value))
//            {
//                is UseCaseResult.Success -> pagingFlow.value = useCaseResult.resultValue.cachedIn(viewModelScope)
//                is UseCaseResult.Error -> Log.d("Debug: SearchRecipeViewModel",
//                    useCaseResult.exception.toString()
//                )
//            }
//        }
//    }
}

class SearchRecipeViewModelFactory(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T {
        return SearchRecipeViewModel(getRecipeListUseCase) as T
    }
}

@Composable
fun Dependency.ViewModel.searchRecipeViewModel(): SearchRecipeViewModel {
    return viewModel(
        key = "SearchRecipeViewModel",
        factory = SearchRecipeViewModelFactory(
            useCase.getRecipeListUseCase()
        )
    )
}