package com.noat.recipe_food2fork.ui.viewModel

import android.util.Log
import androidx.compose.runtime.Composable
import com.noat.recipe_food2fork.dependency.Dependency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.domain.useCase.GetRecipeDetailUseCase
import com.noat.recipe_food2fork.domain.useCase.UseCaseResult
import com.noat.recipe_food2fork.domain.useCase.getRecipeDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipeDetailViewModel(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
): ViewModel(){
    private val uiMutableState = MutableStateFlow(RecipeDetailViewStates.empty)
    val uiState: StateFlow<RecipeDetailViewStates> = uiMutableState

    fun onLaunch(recipeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val useCaseResult = getRecipeDetailUseCase.execute(recipeId))
            {
                is UseCaseResult.Success -> {
                    //handle exception from flow
                    try{
                        useCaseResult.resultValue.collect {
                            uiMutableState.value = uiMutableState.value.copy(
                                loadError = false,
                                recipe = it
                            )
                        }
                    } catch (exception: Exception){
                        uiMutableState.value = uiMutableState.value.copy(
                            loadError = true
                        )
                        Log.d("Exception in RecipeDetailViewModel: onLaunch: Flow", "$exception")
                    }
                }
                is UseCaseResult.Error -> {
                    uiMutableState.value = uiMutableState.value.copy(
                        loadError = true
                    )
                    Log.d("Exception in RecipeDetailViewModel: onLaunch", "${useCaseResult.exception}")
                }
            }
        }
    }
}

data class RecipeDetailViewStates(
    val recipe: RecipeDTO,
    val loadError: Boolean
){
    companion object{
        val empty = RecipeDetailViewStates(
            recipe = RecipeDTO.empty,
            loadError = false
        )
    }
}

class RecipeDetailViewModelFactory(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T {
        return RecipeDetailViewModel(getRecipeDetailUseCase) as T
    }
}

@Composable
fun Dependency.ViewModel.recipeDetailViewModel(): RecipeDetailViewModel
{
    return viewModel(
        key = "RecipeDetailViewModel",
        factory = RecipeDetailViewModelFactory(
            getRecipeDetailUseCase = useCase.getRecipeDetailUseCase()
        )
    )
}

