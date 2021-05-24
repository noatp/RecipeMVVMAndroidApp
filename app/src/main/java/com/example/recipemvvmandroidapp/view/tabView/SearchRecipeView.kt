package com.example.recipemvvmandroidapp.view.tabView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.view.viewComponent.SearchBar
import com.example.recipemvvmandroidapp.viewModel.SearchRecipeViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun CreateSearchRecipeView(
    searchBarText: String,
    onSearchTextChanged: (String) -> Unit,
    recipeList: LazyPagingItems<Recipe>,
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
        LazyColumn {
            items(lazyPagingItems = recipeList){ recipe ->
                RecipeCard(
                    recipeName = recipe!!.title,
                    recipeImageUrl = recipe.featuredImage,
                    onClick = {
                        onClickRecipeCard(recipe.id)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
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
    val searchBarText: String by searchRecipeViewModel.searchBarText.observeAsState(initial = "")
    val recipeList = searchRecipeViewModel.pagingFlow.value.collectAsLazyPagingItems()
    CreateSearchRecipeView(
        searchBarText = searchBarText,
        onSearchTextChanged = searchRecipeViewModel.onSearchTextChanged,
        recipeList = recipeList,
        onSearch = searchRecipeViewModel.onSearch,
        onClickRecipeCard = {recipeId: Int ->
            router.navigateToRecipeDetailView(recipeId)
        }
    )
}

@Preview
@Composable
fun PreviewSearchRecipeView()
{
    CreateSearchRecipeView(
        searchBarText = "chicken",
        onSearchTextChanged = { /*TODO*/ },
        recipeList = flowOf(PagingData.empty<Recipe>()).collectAsLazyPagingItems(),
        onSearch = { /*TODO*/ },
        onClickRecipeCard = {}
    )
}