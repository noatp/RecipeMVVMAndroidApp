package com.example.recipemvvmandroidapp.ui.viewModel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.recipemvvmandroidapp.dependency.Dependency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeDetailUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import com.example.recipemvvmandroidapp.domain.useCase.getRecipeDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase
): ViewModel(){
    data class RecipeForDetailView(
        val title: String,
        val featuredImage: String,
        val ingredients: List<String>
    )

    var recipeForDetailView: MutableState<RecipeForDetailView> = mutableStateOf(
        RecipeForDetailView(
            title = "",
            featuredImage = "",
            ingredients = listOf()
        )
    )

    fun onLaunch(recipeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getRecipeDetailUseCase.execute(recipeId))
            {
                is UseCaseResult.Success -> recipeForDetailView.value = result.resultValue.let{
                    RecipeForDetailView(
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

class RecipeDetailViewModelFactory(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase
): ViewModelProvider.Factory{
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

