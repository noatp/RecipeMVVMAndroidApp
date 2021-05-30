package com.example.recipemvvmandroidapp.ui.view.tabView


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
    lazyPagingItems: LazyPagingItems<RecipeDTO>,
    onClickRecipeCard: (Int) -> Unit
)
{
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
            Spacer(modifier = Modifier.height(12.dp))
        }

        //show a loading indicator at the bottom of the list while appending new items to the list
        if(lazyPagingItems.loadState.append == LoadState.Loading){
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    CircularProgressIndicator()
                }
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
    val lazyPagingItems = discoveryViewModel.pagingFlow.value.collectAsLazyPagingItems()
    CreateDiscoveryView(
        lazyPagingItems = lazyPagingItems,
        onClickRecipeCard = {recipeId: Int ->
            router.navigateToRecipeDetailView(recipeId)
        }
    )
}

@Preview
@Composable
fun PreviewDiscoveryView()
{
    CreateDiscoveryView(
        lazyPagingItems = flowOf(PagingData.empty<RecipeDTO>()).collectAsLazyPagingItems(),
        onClickRecipeCard = {}
    )
}