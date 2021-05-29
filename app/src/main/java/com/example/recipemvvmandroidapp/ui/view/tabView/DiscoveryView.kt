package com.example.recipemvvmandroidapp.ui.view.tabView


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.ui.viewModel.DiscoveryViewModel
import com.example.recipemvvmandroidapp.ui.viewModel.discoveryViewModel

@Composable
fun DiscoveryView(
    recipeList: List<DiscoveryViewModel.RecipeForCardView>,
    onClickRecipeCard: (Int) -> Unit
)
{
    LazyColumn(
        modifier = Modifier.padding(12.dp),
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

@Composable
fun Dependency.View.DiscoveryView(
    router: RouterController
)
{
    val discoveryViewModel: DiscoveryViewModel = viewModel.discoveryViewModel()
    val recipeList = discoveryViewModel.recipeListForCardView.value
    discoveryViewModel.onLaunch()
    DiscoveryView(
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
    DiscoveryView(
        recipeList = listOf(
            DiscoveryViewModel.RecipeForCardView(
            id = 123,
            title = "New recipe",
            featuredImage = "url"
        )),
        onClickRecipeCard = {}
    )
}