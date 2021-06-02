package com.example.recipemvvmandroidapp.ui.view.tabView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.ui.view.viewComponent.SearchBar
import com.example.recipemvvmandroidapp.ui.viewModel.SearchRecipeViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun CreateSearchRecipeView(
    loadError: Boolean,
    searchBarText: String,
    lazyPagingItems: LazyPagingItems<RecipeDTO>,
    onSearchTextChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onClickRecipeCard: (Int) -> Unit,
){
    Column(
        modifier = Modifier.padding(12.dp)
    )
    {
        SearchBar(
            textContent = searchBarText,
            onValueChange = onSearchTextChanged,
            labelContent = "Search recipe",
            onSearch = onSearch
        )
        Spacer(modifier = Modifier.height(12.dp))

        if ((lazyPagingItems.loadState.refresh is LoadState.Error) || loadError ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = "An error occurred",
                    style = MaterialTheme.typography.h5
                )
            }
        }
        else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                //show a loading indicator while waiting for the list to load
                if(lazyPagingItems.loadState.refresh == LoadState.Loading){
                    item{
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            CircularProgressIndicator()
                        }
                    }
                }

                items(lazyPagingItems = lazyPagingItems){ recipe ->
                    RecipeCard(
                        recipeName = recipe!!.title,
                        recipeImageUrl = recipe.featuredImage,
                        onClick = {
                            onClickRecipeCard(recipe.id)
                        }
                    )
                }

                //show a load indicator while loading
                //or error message
                when(lazyPagingItems.loadState.append){
                    is LoadState.Loading -> {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                CircularProgressIndicator()
                            }
                        }
                    }
                    is LoadState.Error -> {
                        item {
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
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun SearchRecipeView(
    router: RouterController,
    searchRecipeViewModel: SearchRecipeViewModel
)
{
    val uiState = searchRecipeViewModel.uiState.collectAsState().value
    val loadError = uiState.loadError
    val searchBarText = uiState.searchBarText
    val lazyPagingItems = uiState.pagingFlow.collectAsLazyPagingItems()
    val onSearchTextChanged = searchRecipeViewModel.onSearchTextChanged
    val onSearch = searchRecipeViewModel.onSearch
    val onClickRecipeCard = {recipeId: Int -> router.navigateToRecipeDetailView(recipeId)}
    CreateSearchRecipeView(
        loadError = loadError,
        searchBarText = searchBarText,
        lazyPagingItems = lazyPagingItems,
        onSearchTextChanged = onSearchTextChanged,
        onSearch = onSearch,
        onClickRecipeCard = onClickRecipeCard,
    )
}

@Preview
@Composable
fun PreviewSearchRecipeView()
{
    CreateSearchRecipeView(
        loadError = false,
        searchBarText = "chicken",
        lazyPagingItems = flowOf(PagingData.empty<RecipeDTO>()).collectAsLazyPagingItems(),
        onSearchTextChanged = { },
        onSearch = { },
        onClickRecipeCard = { }
    )
}