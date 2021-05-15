package com.example.recipemvvmandroidapp.view.tabView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.viewModel.searchRecipeViewModel
import com.example.recipemvvmandroidapp.view.viewComponent.SearchBar
import com.example.recipemvvmandroidapp.viewModel.SearchRecipeViewModel

@Composable
fun SearchRecipeView(
    searchBarText: String,
    onSearchTextChanged: (String) -> Unit,
    recipeList: List<SearchRecipeViewModel.RecipeForCardViewInViewModel>,
    onSearch: () -> Unit,
    onClickRecipeCard: (Int) -> Unit
){
    Scaffold(
        modifier = Modifier,
        topBar = {
        },
        content = {
            Column(
                modifier = Modifier
            )
            {
                SearchBar(
                    textContent = searchBarText,
                    onValueChange = onSearchTextChanged,
                    labelContent = "Search recipe",
                    onSearch = onSearch
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn() {
                    itemsIndexed(items = recipeList){ index, recipe ->
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
    )
}

@Composable
fun Dependency.View.CreateSearchRecipeView(
    searchRecipeViewModel: SearchRecipeViewModel,
    router: RouterController
)
{
    val searchBarText: String by searchRecipeViewModel.searchBarText.observeAsState(initial = "")
    val recipeList = searchRecipeViewModel.recipeListForCardView.value
    SearchRecipeView(
        searchBarText = searchBarText,
        onSearchTextChanged = searchRecipeViewModel.onSearchTextChanged,
        recipeList = recipeList,
        onSearch = searchRecipeViewModel.onSearch,
        onClickRecipeCard = {recipeId: Int ->
            router.navigateToRecipeDetailView(recipeId)
        }
    )
}

@Composable
fun Dependency.View.SearchRecipeView(router: RouterController)
{
    val searchRecipeViewModel = viewModel.searchRecipeViewModel()
    CreateSearchRecipeView(
        searchRecipeViewModel = searchRecipeViewModel,
        router = router
    )
}

@Preview
@Composable
fun PreviewSearchRecipeView()
{
    SearchRecipeView(
        searchBarText = "chicken",
        onSearchTextChanged = { /*TODO*/ },
        recipeList = listOf(SearchRecipeViewModel.RecipeForCardViewInViewModel(
            id = 123,
            title = "New recipe",
            featuredImage = "https://nyc3.digitaloceanspaces.com/food2fork/food2fork-static/featured_images/583/featured_image.png"
        )),
        onSearch = { /*TODO*/ },
        onClickRecipeCard = {}
    )
}