package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel(){
    var pagingFlow: MutableState<Flow<PagingData<Recipe>>> = mutableStateOf(getRecipeListOnLaunch())

    private fun getRecipeListOnLaunch(): Flow<PagingData<Recipe>> {
        return when(val useCaseResult = getRecipeListUseCase.execute("")){
            is UseCaseResult.Success -> {
                useCaseResult.resultValue
            }
            is UseCaseResult.Error -> {
                Log.d("Debug: DiscoveryViewModel", useCaseResult.exception.toString())
                flowOf(PagingData.empty())
            }
        }
    }
}

