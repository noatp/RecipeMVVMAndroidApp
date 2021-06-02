package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeDetailUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val recipeId = savedStateHandle.get<Int>("recipeId")

    private val uiMutableState = MutableStateFlow(RecipeDetailViewStates.empty)
    val uiState: StateFlow<RecipeDetailViewStates> = uiMutableState

    init{
        getRecipeDetail()
    }

    private fun getRecipeDetail()
    {
        recipeId?.let {
            try{
                viewModelScope.launch(Dispatchers.IO) {
                    when(val useCaseResult = getRecipeDetailUseCase.execute(it)){
                        is UseCaseResult.Success -> uiMutableState.value = uiMutableState.value.copy(
                            recipeForDetailView = useCaseResult.resultValue.let {
                                RecipeForDetailView(
                                    title = it.title,
                                    featuredImage = it.featuredImage,
                                    ingredients = it.ingredients
                                )
                            },
                            loadError = false
                        )
                        is UseCaseResult.Error -> throw useCaseResult.exception
                    }
                }
            } catch (exception: Exception){
                Log.d("Exception in RecipeDetailViewModel: init", "$exception")
                uiMutableState.value = uiMutableState.value.copy(
                    loadError = true
                )
            }

        }
    }
}

data class RecipeForDetailView(
    val title: String,
    val featuredImage: String,
    val ingredients: List<String>
){
    companion object{
        val empty = RecipeForDetailView(
            title = "",
            featuredImage = "",
            ingredients = emptyList()
        )
    }
}

data class RecipeDetailViewStates(
    val recipeForDetailView: RecipeForDetailView,
    val loadError: Boolean
){
    companion object{
        val empty = RecipeDetailViewStates(
            recipeForDetailView = RecipeForDetailView.empty,
            loadError = false
        )
    }
}
