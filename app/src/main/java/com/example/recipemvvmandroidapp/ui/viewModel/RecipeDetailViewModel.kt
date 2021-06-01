package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeDetailUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private val mutableUiState: MutableStateFlow<UiState<RecipeForDetailView>> =
        MutableStateFlow(UiState.Success(RecipeForDetailView.empty))
    val uiState: StateFlow<UiState<RecipeForDetailView>> = mutableUiState

    init{
        recipeId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try{
                    when(val result = getRecipeDetailUseCase.execute(it))
                    {
                        is UseCaseResult.Success -> {
                            result.resultValue.let{
                                mutableUiState.value = UiState.Success(
                                    RecipeForDetailView(
                                        title = it.title,
                                        featuredImage = it.featuredImage,
                                        ingredients = it.ingredients
                                    )
                                )
                            }
                        }
                        is UseCaseResult.Error -> {
                            throw(result.exception)
                        }
                    }
                } catch (exception: Exception){
                    Log.d("Exception in RecipeDetailViewModel: init", "$exception")
                    mutableUiState.value = UiState.Error(exception = exception)
                }
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
