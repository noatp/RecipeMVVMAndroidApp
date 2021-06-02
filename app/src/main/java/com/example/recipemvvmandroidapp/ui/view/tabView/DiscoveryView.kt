package com.example.recipemvvmandroidapp.ui.view.tabView


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.ui.viewModel.DiscoveryViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun CreateDiscoveryView(
    loadError: Boolean,
    lazyPagingItems: LazyPagingItems<RecipeDTO>,
    onClickRecipeCard: (Int) -> Unit
)
{
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
    else{
        LazyColumn(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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

            //show a loading indicator at the bottom of the list while appending new items to the list
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

@Composable
fun DiscoveryView(
    router: RouterController,
    discoveryViewModel: DiscoveryViewModel
)
{
    val uiState = discoveryViewModel.uiState.collectAsState().value
    val loadError = uiState.loadError
    val lazyPagingItems = uiState.pagingFlow.collectAsLazyPagingItems()
    val onClickRecipeCard = {recipeId: Int -> router.navigateToRecipeDetailView(recipeId)}
    CreateDiscoveryView(
        loadError = loadError,
        lazyPagingItems = lazyPagingItems,
        onClickRecipeCard = onClickRecipeCard
    )
}

@Preview
@Composable
fun PreviewDiscoveryView()
{
    CreateDiscoveryView(
        loadError = false,
        lazyPagingItems = flowOf(PagingData.empty<RecipeDTO>()).collectAsLazyPagingItems(),
        onClickRecipeCard = { }
    )
}