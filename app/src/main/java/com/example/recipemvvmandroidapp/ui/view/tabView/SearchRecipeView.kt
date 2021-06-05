package com.example.recipemvvmandroidapp.ui.view.tabView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.ui.viewModel.searchRecipeViewModel
import com.example.recipemvvmandroidapp.ui.view.viewComponent.SearchBar
import kotlinx.coroutines.flow.flowOf

@ExperimentalMaterialApi
@Composable
fun SearchRecipeView(
    onSearchBarTextChanged: (String) -> Unit,
    checkIfNewPageIsNeeded: () -> Unit,
    searchBarText: String,
    recipeList: List<RecipeDTO>,
    lazyListState: LazyListState,
    isLoading: Boolean,
    loadError: Boolean,
    onClickRecipeCard: (Int) -> Unit
){
    Column(
        modifier = Modifier.padding(12.dp)
    )
    {
        SearchBar(
            textContent = searchBarText,
            onValueChange = onSearchBarTextChanged,
            labelContent = "Search recipe",
            onSearch = { }
        )
        Spacer(modifier = Modifier.height(12.dp))
        checkIfNewPageIsNeeded()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = lazyListState
        ) {
            items(recipeList){ recipe ->
                RecipeCard(
                    recipeName = recipe.title,
                    recipeImageUrl = recipe.featuredImage,
                    onClick = {
                        onClickRecipeCard(recipe.id)
                    }
                )
            }
            if(isLoading){
                item{
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        CircularProgressIndicator()
                    }
                }
            }
            if(loadError){
                item{
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = "An error occurred",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Dependency.View.SearchRecipeView(
    router: RouterController
)
{
    val searchRecipeViewModel = viewModel.searchRecipeViewModel()
    val onSearchBarTextChanged = searchRecipeViewModel.onSearchBarTextChanged
    val checkIfNewPageIsNeeded = searchRecipeViewModel.checkIfNewPageIsNeeded
    val uiState = searchRecipeViewModel.uiState.collectAsState().value
    val searchBarText = uiState.searchBarText
    val recipeList = uiState.recipeList
    val lazyListState = uiState.lazyListState
    val isLoading = uiState.isLoading
    val loadError = uiState.loadError
    SearchRecipeView(
        onSearchBarTextChanged = onSearchBarTextChanged,
        checkIfNewPageIsNeeded = checkIfNewPageIsNeeded,
        searchBarText = searchBarText,
        recipeList = recipeList,
        lazyListState = lazyListState,
        isLoading = isLoading,
        loadError = loadError,
        onClickRecipeCard = {recipeId: Int ->
            router.navigateToRecipeDetailView(recipeId)
        }
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewSearchRecipeView()
{
    SearchRecipeView(
        onSearchBarTextChanged = { },
        checkIfNewPageIsNeeded = { },
        searchBarText = "searchBarText",
        recipeList = listOf(),
        lazyListState = LazyListState(),
        isLoading = false,
        loadError = false,
        onClickRecipeCard = { }
    )
}