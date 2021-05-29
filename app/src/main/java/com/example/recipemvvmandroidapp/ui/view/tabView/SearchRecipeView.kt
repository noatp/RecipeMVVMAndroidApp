package com.example.recipemvvmandroidapp.ui.view.tabView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.ui.viewModel.searchRecipeViewModel
import com.example.recipemvvmandroidapp.ui.view.viewComponent.SearchBar
import com.example.recipemvvmandroidapp.ui.viewModel.SearchRecipeViewModel

@Composable
fun SearchRecipeView(
    searchBarText: String,
    onSearchTextChanged: (String) -> Unit,
    recipeList: List<SearchRecipeViewModel.RecipeForCardView>,
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
            items(items = recipeList){ recipe ->
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

@Composable
fun Dependency.View.SearchRecipeView(
    router: RouterController
)
{
    val searchRecipeViewModel = viewModel.searchRecipeViewModel()
    val onSearchTextChanged = searchRecipeViewModel.onSearchTextChanged
    val searchBarText = searchRecipeViewModel.searchBarText.value
    val recipeList = searchRecipeViewModel.recipeListForCardView.value
    val onSearch = searchRecipeViewModel.onSearch
    SearchRecipeView(
        searchBarText = searchBarText,
        onSearchTextChanged = onSearchTextChanged,
        recipeList = recipeList,
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
        recipeList = listOf(
            SearchRecipeViewModel.RecipeForCardView(
            id = 123,
            title = "New recipe",
            featuredImage = "url"
        )),
        onSearch = { /*TODO*/ },
        onClickRecipeCard = {}
    )
}