package com.example.recipemvvmandroidapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.getRecipeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {
    companion object{
        var instance: SearchRecipeViewModel? = null
    }

    data class RecipeForCardViewInViewModel(
        val id: Int,
        val title: String,
        val featuredImage: String
    )

    //data for search bar
    private val _searchBarText = MutableLiveData("")
    val searchBarText: LiveData<String> = _searchBarText

    //data for lazy list
    var recipeListForCardView: MutableState<List<RecipeForCardViewInViewModel>> = mutableStateOf(listOf())

    private var pageIndex: Int = 1

    //event for search bar
    val onSearchTextChange: (String) -> Unit = {
        _searchBarText.value = it
    }

    val onSearch: () -> Unit = {
        viewModelScope.launch(Dispatchers.IO) {
            val searchResult = getRecipeListUseCase
                .execute(
                    page = pageIndex,
                    query = searchBarText.value ?: ""
                )
            when(searchResult)
            {
                is GetRecipeListUseCase.Result.Success -> {
                    recipeListForCardView.value = searchResult.resultValue.map{
                        RecipeForCardViewInViewModel(
                            id = it.id,
                            title = it.title,
                            featuredImage = it.featuredImage
                        )
                    }
                }
                is GetRecipeListUseCase.Result.Error -> Log.d("Debug: SearchRecipeViewModel",
                    searchResult.exception.toString()
                )
            }
        }
    }
}

fun Dependency.ViewModel.searchRecipeViewModel(): SearchRecipeViewModel{
    if(SearchRecipeViewModel.instance == null)
        SearchRecipeViewModel.instance = SearchRecipeViewModel(useCase.getRecipeListUseCase())
    return SearchRecipeViewModel.instance!!
}