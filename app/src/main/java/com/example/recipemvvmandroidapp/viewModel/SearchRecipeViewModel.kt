package com.example.recipemvvmandroidapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {
    data class RecipeForCardView(
        val id: Int,
        val title: String,
        val featuredImage: String
    )

    //data for search bar
    private val _searchBarText = MutableLiveData("")
    val searchBarText: LiveData<String> = _searchBarText

    //data for lazy list
    var pagingFlow: MutableState<Flow<PagingData<Recipe>>> = mutableStateOf(flowOf(PagingData.empty()))

    //event for search bar
    val onSearchTextChanged: (String) -> Unit = {
        _searchBarText.value = it
    }

    val onSearch: () -> Unit = {
        when(val useCaseResult = getRecipeListUseCase.execute(searchBarText.value ?: "")){
            is UseCaseResult.Success -> pagingFlow.value = useCaseResult.resultValue
            is UseCaseResult.Error -> Log.d("Debug: SearchRecipeViewModel",
                useCaseResult.exception.toString()
            )
        }
    }
}

//class SearchRecipeViewModelFactory(
//    private val getRecipeListUseCase: GetRecipeListUseCase
//): ViewModelProvider.Factory{
//    override fun <T : ViewModel?> create(
//        modelClass: Class<T>
//    ): T {
//        return SearchRecipeViewModel(getRecipeListUseCase) as T
//    }
//}