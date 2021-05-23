package com.example.recipemvvmandroidapp.view.tabView


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.viewModel.DiscoveryViewModel

@Composable
fun CreateDiscoveryView(
    recipeList: List<DiscoveryViewModel.RecipeForCardView>,
    onClickRecipeCard: (Int) -> Unit
)
{
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

@Composable
fun DiscoveryView(
    router: RouterController,
    discoveryViewModel: DiscoveryViewModel
)
{
//    val discoveryViewModel: DiscoveryViewModel = viewModel()
    val recipeList = discoveryViewModel.recipeListForCardView.value
    discoveryViewModel.onLaunch()
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
        recipeList = listOf(
            DiscoveryViewModel.RecipeForCardView(
            id = 123,
            title = "New recipe",
            featuredImage = "url"
        )),
        onClickRecipeCard = {}
    )
}