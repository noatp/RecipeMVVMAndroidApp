package com.example.recipemvvmandroidapp.view.tabView


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
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
import com.example.recipemvvmandroidapp.viewModel.DiscoveryViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun CreateDiscoveryView(
    recipeList: LazyPagingItems<Recipe>,
    onClickRecipeCard: (Int) -> Unit
)
{
    LazyColumn(
        modifier = Modifier.padding(12.dp)
    ) {
        items(lazyPagingItems = recipeList){recipe ->
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

@Composable
fun DiscoveryView(
    router: RouterController,
    discoveryViewModel: DiscoveryViewModel
)
{
    val recipeList = discoveryViewModel.pagingFlow.value.collectAsLazyPagingItems()
    CreateDiscoveryView(
        recipeList = recipeList,
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
        recipeList = flowOf(PagingData.empty<Recipe>()).collectAsLazyPagingItems(),
        onClickRecipeCard = {}
    )
}