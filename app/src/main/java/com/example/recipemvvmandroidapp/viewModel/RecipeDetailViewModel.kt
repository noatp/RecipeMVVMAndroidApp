package com.example.recipemvvmandroidapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.recipemvvmandroidapp.dependency.Dependency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeDetailUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import com.example.recipemvvmandroidapp.domain.useCase.getRecipeDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase
): ViewModel(){
    companion object{
        var instance: RecipeDetailViewModel? = null
    }

    data class RecipeForDetailViewInViewModel(
        val title: String,
        val featuredImage: String,
        val ingredients: List<String>
    )

    var recipeForDetailView: MutableState<RecipeForDetailViewInViewModel> = mutableStateOf(
        RecipeForDetailViewInViewModel(
            title = "",
            featuredImage = "",
            ingredients = listOf()
        )
    )

    fun onLaunch(recipeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = getRecipeDetailUseCase.execute(recipeId)
            when(result)
            {
                is UseCaseResult.Success -> recipeForDetailView.value = result.resultValue.let{
                    RecipeForDetailViewInViewModel(
                        title = it.title,
                        featuredImage = it.featuredImage,
                        ingredients = it.ingredients
                    )
                }
                is UseCaseResult.Error -> Log.d("Debug: RecipeDetailViewModel",
                    result.exception.toString()
                )
            }
        }
    }
}

fun Dependency.ViewModel.recipeDetailViewModel(): RecipeDetailViewModel
{
    if(RecipeDetailViewModel.instance == null)
    {
        RecipeDetailViewModel.instance = RecipeDetailViewModel(useCase.getRecipeDetailUseCase())
    }
    return RecipeDetailViewModel.instance!!
}

