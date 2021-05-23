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
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.view.viewComponent.RecipeCard
import com.example.recipemvvmandroidapp.viewModel.DiscoveryViewModel

@Composable
fun CreateDiscoveryView(
    recipeList: List<DiscoveryViewModel.RecipeForCardView>,
    onClickRecipeCard: (Int) -> Unit
)
{
    LazyColumn(
        modifier = Modifier.padding(12.dp)
    ) {
        itemsIndexed(items = recipeList){ index, recipe ->
            RecipeCard(
                recipeName = recipe.title,
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