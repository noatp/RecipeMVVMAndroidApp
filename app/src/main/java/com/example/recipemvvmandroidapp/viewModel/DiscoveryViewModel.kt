package com.example.recipemvvmandroidapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel(){
    data class RecipeForCardView(
        val id: Int,
        val title: String,
        val featuredImage: String
    )

    var recipeListForCardView: MutableState<List<RecipeForCardView>> = mutableStateOf(listOf())

    fun onLaunch(){
        viewModelScope.launch (Dispatchers.IO){
            when(val result = getRecipeListUseCase
                .execute(
                    page = 1,
                    query = "a"
                )
            )
            {
                is UseCaseResult.Success -> {
                    recipeListForCardView.value = result.resultValue.map{
                        RecipeForCardView(
                            id = it.id,
                            title = it.title,
                            featuredImage = it.featuredImage
                        )
                    }
                }
                is UseCaseResult.Error -> Log.d("Debug: DiscoveryViewModel",
                    result.exception.toString()
                )
            }
        }
    }
}

class DiscoveryViewModelFactory(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T {
        return DiscoveryViewModel(getRecipeListUseCase) as T
    }
}
