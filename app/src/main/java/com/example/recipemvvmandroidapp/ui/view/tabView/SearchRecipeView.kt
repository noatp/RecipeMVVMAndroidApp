package com.example.recipemvvmandroidapp.ui.view.tabView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.ui.viewModel.searchRecipeViewModel
import com.example.recipemvvmandroidapp.ui.view.viewComponent.SearchBar
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchRecipeView(
    searchBarText: String,
    onSearchTextChanged: (String) -> Unit,
    lazyPagingItems: LazyPagingItems<Recipe>,
    onSearch: () -> Unit,
    onClickRecipeCard: (Int) -> Unit
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(lazyPagingItems = lazyPagingItems){ recipe ->
                recipe?.let{
                    RecipeCard(
                        recipeName = recipe.title,
                        recipeImageUrl = recipe.featuredImage,
                        onClick = {
                            onClickRecipeCard(recipe.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Dependency.View.SearchRecipeView(
    router: RouterController
)
{
    val searchRecipeViewModel = viewModel.searchRecipeViewModel()
    val onSearchTextChanged = searchRecipeViewModel.onSearchTextChanged
    val searchBarText = searchRecipeViewModel.searchBarText.value
    val lazyPagingItems = searchRecipeViewModel.pagingFlow.value.collectAsLazyPagingItems()
    val onSearch = searchRecipeViewModel.onSearch
    SearchRecipeView(
        searchBarText = searchBarText,
        onSearchTextChanged = onSearchTextChanged,
        lazyPagingItems = lazyPagingItems,
        onSearch = onSearch,
        onClickRecipeCard = {recipeId: Int ->
            router.navigateToRecipeDetailView(recipeId)
        }
    )
}

@Preview
@Composable
fun PreviewSearchRecipeView()
{
    SearchRecipeView(
        searchBarText = "chicken",
        onSearchTextChanged = { /*TODO*/ },
        lazyPagingItems = flowOf(PagingData.empty<Recipe>()).collectAsLazyPagingItems(),
        onSearch = { },
        onClickRecipeCard = {}
    )
}