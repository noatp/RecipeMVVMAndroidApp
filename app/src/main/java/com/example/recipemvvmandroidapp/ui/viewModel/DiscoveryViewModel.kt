package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import com.example.recipemvvmandroidapp.domain.useCase.getRecipeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val API_PAGE_SIZE = 30

class DiscoveryViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel(){
    var recipeList: MutableState<List<RecipeDTO>> = mutableStateOf(listOf())

    val lazyListState: LazyListState = LazyListState()

    private var page = 1

    val isLoading = mutableStateOf(false)

    init{
        initialLoad()
    }

    private fun initialLoad(){
        viewModelScope.launch(Dispatchers.IO){
            when(val useCaseResult = getRecipeListUseCase.execute(
                page = 1,
                query = "a"
            ))
            {
                is UseCaseResult.Success -> {
                    recipeList.value = useCaseResult.resultValue
                }
                is UseCaseResult.Error -> Log.d("Debug: DiscoveryViewModel",
                    useCaseResult.exception.toString()
                )
            }
        }
    }

    fun checkIfNewPageIsNeeded(){
        if(lazyListState.firstVisibleItemIndex + API_PAGE_SIZE > page * API_PAGE_SIZE){
            if(!isLoading.value){
                isLoading.value = true
                page += 1
                if(page > 1){
                    viewModelScope.launch(Dispatchers.IO){
                        when(val useCaseResult = getRecipeListUseCase.execute(
                            page = page,
                            query = "a"
                        ))
                        {
                            is UseCaseResult.Success -> {
                                appendNewPage(useCaseResult.resultValue)
                                isLoading.value = false
                            }
                            is UseCaseResult.Error -> {
                                isLoading.value = false
                                Log.d("Debug: DiscoveryViewModel",
                                    useCaseResult.exception.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun appendNewPage(newRecipeList: List<RecipeDTO>){
        val currentRecipeList = ArrayList(recipeList.value)
        currentRecipeList.addAll(newRecipeList)
        recipeList.value = currentRecipeList
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