package com.example.recipemvvmandroidapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import com.example.recipemvvmandroidapp.domain.useCase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

    var pagingFlow: MutableState<Flow<PagingData<Recipe>>> = mutableStateOf(flowOf(PagingData.empty()))

    init {
        when(val useCaseResult = getRecipeListUseCase.execute("c")){
            is UseCaseResult.Success -> pagingFlow.value = useCaseResult.resultValue
            is UseCaseResult.Error -> Log.d("Debug: DiscoveryViewModel",
                useCaseResult.exception.toString()
            )
        }
    }
}

//class DiscoveryViewModelFactory(
//    private val getRecipeListUseCase: GetRecipeListUseCase
//): ViewModelProvider.Factory{
//    override fun <T : ViewModel?> create(
//        modelClass: Class<T>
//    ): T {
//        return DiscoveryViewModel(getRecipeListUseCase) as T
//    }
//}
