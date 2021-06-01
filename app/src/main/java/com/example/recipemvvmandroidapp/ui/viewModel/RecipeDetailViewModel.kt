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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    data class RecipeForDetailView(
        val title: String,
        val featuredImage: String,
        val ingredients: List<String>
    )

    private val recipeId = savedStateHandle.get<Int>("recipeId")

    var loadError: MutableState<Boolean> = mutableStateOf(false)

    var recipeForDetailView: MutableState<RecipeForDetailView> = mutableStateOf(
        RecipeForDetailView(
            title = "",
            featuredImage = "",
            ingredients = listOf()
        )
    )

    init{
        recipeId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                when(val result = getRecipeDetailUseCase.execute(it))
                {
                    is UseCaseResult.Success -> {
                        recipeForDetailView.value = result.resultValue.let{
                            RecipeForDetailView(
                                title = it.title,
                                featuredImage = it.featuredImage,
                                ingredients = it.ingredients
                            )
                        }
                        loadError.value = false
                    }
                    is UseCaseResult.Error -> {
                        Log.d("Debug: RecipeDetailViewModel", result.exception.toString())
                        loadError.value = true
                    }
                }
            }
        }
    }
}

//class RecipeDetailViewModelFactory(
//    private val getRecipeDetailUseCase: GetRecipeDetailUseCase
//): ViewModelProvider.Factory{
//    override fun <T : ViewModel?> create(
//        modelClass: Class<T>
//    ): T {
//        return RecipeDetailViewModel(getRecipeDetailUseCase) as T
//    }
//}
