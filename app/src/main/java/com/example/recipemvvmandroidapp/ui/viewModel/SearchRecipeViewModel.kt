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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {
//    //data for search bar
//    val searchBarText: MutableState<String> = mutableStateOf("")
//
//    //data for lazy list
//    val pagingFlow: MutableState<Flow<PagingData<RecipeDTO>>> = mutableStateOf(flowOf(PagingData.empty()))
//
//    //scroll state for lazy list
//    val lazyListState: MutableState<LazyListState> = mutableStateOf(LazyListState())

    private val uiMutableState = MutableStateFlow(SearchRecipeViewStates.empty)
    val uiState: StateFlow<SearchRecipeViewStates> = uiMutableState

    val onSearch: () -> Unit = {
        try{
            when(val useCaseResult = getRecipeListUseCase.execute(uiMutableState.value.searchBarText)){
                is UseCaseResult.Success -> uiMutableState.value = uiMutableState.value.copy(
                    pagingFlow = useCaseResult.resultValue.cachedIn(viewModelScope),
                    loadError = false
                )
                is UseCaseResult.Error -> throw useCaseResult.exception
            }
        } catch (exception: Exception){
            Log.d("Exception in SearchRecipeViewModel: onSearch", "$exception")
            uiMutableState.value = uiMutableState.value.copy(
                loadError = true
            )
        }

    }
    val onSearchTextChanged: (String) -> Unit = {
        uiMutableState.value = uiMutableState.value.copy(
            searchBarText = it
        )
    }
}

data class SearchRecipeViewStates(
    val searchBarText: String,
    val pagingFlow: Flow<PagingData<RecipeDTO>>,
    val loadError: Boolean
){
    companion object{
        val empty = SearchRecipeViewStates(
            searchBarText = "",
            pagingFlow = flowOf(PagingData.empty()),
            loadError = false
        )
    }
}
