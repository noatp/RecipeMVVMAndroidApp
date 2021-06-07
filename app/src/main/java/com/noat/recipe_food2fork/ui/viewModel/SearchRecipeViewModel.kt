package com.noat.recipe_food2fork.ui.viewModel

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.domain.useCase.GetRecipeListUseCase
import com.noat.recipe_food2fork.domain.useCase.UseCaseResult
import com.noat.recipe_food2fork.domain.useCase.getRecipeListUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchRecipeViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {
    private val uiMutableState = MutableStateFlow(SearchRecipeViewStates.empty)
    val uiState: StateFlow<SearchRecipeViewStates> = uiMutableState

    private var mostRecentlyLoadedPage = 0
    private var hasNextPage: Boolean = true

    private var currentNetworkJob: Job = Job()

    //event for search bar
    val onSearchBarTextChanged: (String) -> Unit = { newString: String ->
        try{
            currentNetworkJob.cancel()
        } catch (exception: Exception){
            Log.d("Exception in SearchRecipeViewModel: onSearchBarTextChanged", "$exception")
        }
        uiMutableState.value = SearchRecipeViewStates(
            searchBarText = newString,
            recipeList = listOf(),
            lazyListState = LazyListState(),
            isLoading = false,
            loadError = false
        )
        mostRecentlyLoadedPage = 0
        hasNextPage = true
    }

    val checkIfNewPageIsNeeded: () -> Unit = {
        //this check will let pagination always load 1 page a head
        //ex: firstVisibleItemIndex = 1, page = 1 => 1 + 30 > 1 * 30 => load page 2
        if(uiMutableState.value.lazyListState.firstVisibleItemIndex + API_PAGE_SIZE > mostRecentlyLoadedPage * API_PAGE_SIZE){
            if(!uiMutableState.value.isLoading){
                if(hasNextPage){
                    uiMutableState.value = uiMutableState.value.copy(
                        isLoading = true
                    )
                    mostRecentlyLoadedPage += 1
                    currentNetworkJob = viewModelScope.launch(Dispatchers.IO){
                        delay(100)
                        println("Job launch with query: ${uiMutableState.value.searchBarText}")
                        when(val useCaseResult = getRecipeListUseCase.execute(
                            page = mostRecentlyLoadedPage,
                            query = uiMutableState.value.searchBarText
                        ))
                        {
                            is UseCaseResult.Success -> {
                                uiMutableState.value = uiMutableState.value.copy(
                                    loadError = false
                                )
                                hasNextPage = useCaseResult.resultValue.hasNextPage
                                appendNewPage(useCaseResult.resultValue.recipeList)
                            }
                            is UseCaseResult.Error -> {
                                if(useCaseResult.exception !is CancellationException){
                                    uiMutableState.value = uiMutableState.value.copy(
                                        loadError = true
                                    )
                                }
                                Log.d("Exception in SearchRecipeViewmodel: checkIfNewPageIsNeeded", "${useCaseResult.exception}")
                            }
                        }
                        uiMutableState.value = uiMutableState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun appendNewPage(newRecipeList: List<RecipeDTO>){
        val currentRecipeList = ArrayList(uiMutableState.value.recipeList)
        currentRecipeList.addAll(newRecipeList)
        uiMutableState.value = uiMutableState.value.copy(
            recipeList = currentRecipeList
        )
    }
}

data class SearchRecipeViewStates(
    val searchBarText: String,
    val recipeList: List<RecipeDTO>,
    val lazyListState: LazyListState,
    val isLoading: Boolean,
    val loadError: Boolean
){
    companion object{
        val empty = SearchRecipeViewStates(
            searchBarText = "",
            recipeList = listOf(),
            lazyListState = LazyListState(),
            isLoading = false,
            loadError = false
        )
    }
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