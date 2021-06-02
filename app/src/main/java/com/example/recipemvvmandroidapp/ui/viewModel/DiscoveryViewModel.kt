package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
class DiscoveryViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel(){
    private val uiMutableState = MutableStateFlow(DiscoveryViewStates.empty)
    val uiState: StateFlow<DiscoveryViewStates> = uiMutableState

    init{
        getRecipeList()
    }

    private fun getRecipeList() {
        try{
            when(val useCaseResult = getRecipeListUseCase.execute("")){
                is UseCaseResult.Success -> uiMutableState.value = uiMutableState.value.copy(
                    pagingFlow = useCaseResult.resultValue.cachedIn(viewModelScope),
                    loadError = false
                )
                is UseCaseResult.Error -> throw useCaseResult.exception
            }
        } catch (exception: Exception){
            Log.d("Exception in DiscoveryViewModel: searchRecipeList", "$exception")
            uiMutableState.value = uiMutableState.value.copy(
                loadError = true
            )
        }
    }
}

data class DiscoveryViewStates(
    val pagingFlow: Flow<PagingData<RecipeDTO>>,
    val loadError: Boolean
){
    companion object{
        val empty = DiscoveryViewStates(
            pagingFlow = flowOf(PagingData.empty()),
            loadError = false
        )
    }
}

