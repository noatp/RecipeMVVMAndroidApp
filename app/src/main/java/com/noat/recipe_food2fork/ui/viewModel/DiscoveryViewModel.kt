package com.noat.recipe_food2fork.ui.viewModel

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.domain.useCase.GetRecipeListUseCase
import com.noat.recipe_food2fork.domain.useCase.UseCaseResult
import com.noat.recipe_food2fork.domain.useCase.getRecipeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val API_PAGE_SIZE = 30

class DiscoveryViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel(){
    private val uiMutableState = MutableStateFlow(DiscoveryViewStates.empty)
    val uiState: StateFlow<DiscoveryViewStates> = uiMutableState

    private var mostRecentlyLoadedPage: Int = 0
    private var hasNextPage: Boolean = true

    //I won't be using this for now
    //private var hasPreviousPage = false

    val checkIfNewPageIsNeeded: () -> Unit = {
        //this check will let pagination always load 1 page a head
        //ex: firstVisibleItemIndex = 1, page = 1 => 1 + 30 > 1 * 30 => load page 2
        if(uiMutableState.value.lazyListState.firstVisibleItemIndex + API_PAGE_SIZE > mostRecentlyLoadedPage * API_PAGE_SIZE){
            //check if the there is already a fetch in process, can check if coroutine job is active or not?
            if(!uiMutableState.value.isLoading){
                //check if the current page has a next page to prevent a load error
                if(hasNextPage){
                    uiMutableState.value = uiMutableState.value.copy(
                        isLoading = true
                    )
                    mostRecentlyLoadedPage += 1
                    viewModelScope.launch(Dispatchers.IO){
                        delay(500)
                        when(val useCaseResult = getRecipeListUseCase.execute(
                            page = mostRecentlyLoadedPage,
                            query = "a"
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
                                uiMutableState.value = uiMutableState.value.copy(
                                    loadError = true
                                )
                                Log.d("Exception in DiscoveryViewModel: checkIfNewPageIsNeeded", "${useCaseResult.exception}")
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

data class DiscoveryViewStates(
    val recipeList: List<RecipeDTO>,
    val lazyListState: LazyListState,
    val isLoading: Boolean,
    val loadError: Boolean
){
    companion object{
        val empty = DiscoveryViewStates(
            recipeList = listOf(),
            lazyListState = LazyListState(),
            isLoading = false,
            loadError = false
        )
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