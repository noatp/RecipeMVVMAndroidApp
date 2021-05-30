package com.example.recipemvvmandroidapp.ui.view.tabView


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
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
import com.example.recipemvvmandroidapp.ui.viewModel.DiscoveryViewModel
import com.example.recipemvvmandroidapp.ui.viewModel.discoveryViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun DiscoveryView(
    lazyPagingItems: LazyPagingItems<Recipe>,
    onClickRecipeCard: (Int) -> Unit
)
{
    LazyColumn(
        modifier = Modifier.padding(12.dp),
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

@Composable
fun Dependency.View.DiscoveryView(
    router: RouterController
)
{
    val discoveryViewModel: DiscoveryViewModel = viewModel.discoveryViewModel()
    val lazyPagingItems = discoveryViewModel.pagingFlow.value.collectAsLazyPagingItems()
    DiscoveryView(
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
    DiscoveryView(
        lazyPagingItems = flowOf(PagingData.empty<Recipe>()).collectAsLazyPagingItems(),
        onClickRecipeCard = {}
    )
}