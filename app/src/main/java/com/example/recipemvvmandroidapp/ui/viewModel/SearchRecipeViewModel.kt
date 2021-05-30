package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {
    //data for search bar
    val searchBarText: MutableState<String> = mutableStateOf("")
    //event for search bar
    val onSearchTextChanged: (String) -> Unit = {
        searchBarText.value = it
    }

    //data for lazy list
    val pagingFlow: MutableState<Flow<PagingData<RecipeDTO>>> = mutableStateOf(flowOf(PagingData.empty()))

    //scroll state for lazy list
    val lazyListState: MutableState<LazyListState> = mutableStateOf(LazyListState())

    val onSearch: () -> Unit = {
        when(val useCaseResult = getRecipeListUseCase.execute(searchBarText.value)){
            is UseCaseResult.Success -> pagingFlow.value = useCaseResult.resultValue.cachedIn(viewModelScope)
            is UseCaseResult.Error -> Log.d("Debug: SearchRecipeViewModel",
                useCaseResult.exception.toString()
            )
        }
    }
}
