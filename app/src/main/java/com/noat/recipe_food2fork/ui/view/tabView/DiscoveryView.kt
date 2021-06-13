package com.noat.recipe_food2fork.ui.view.tabView


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noat.recipe_food2fork.domain.model.RecipeDTO
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.ui.router.RouterController
import com.noat.recipe_food2fork.ui.view.viewComponent.RecipeCard
import com.noat.recipe_food2fork.ui.viewModel.DiscoveryViewModel
import com.noat.recipe_food2fork.ui.viewModel.discoveryViewModel

@ExperimentalMaterialApi
@Composable
fun DiscoveryView(
    recipeList: List<RecipeDTO>,
    lazyListState: LazyListState,
    isLoading: Boolean,
    loadError: Boolean,
    checkIfNewPageIsNeeded: () -> Unit,
    onClickRecipeCard: (Int) -> Unit,
)
{
    checkIfNewPageIsNeeded()
    LazyColumn(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyListState
    ) {
        items(recipeList){ recipe ->
            RecipeCard(
                recipeName = recipe.title,
                recipeImageUrl = recipe.featuredImage,
                onClick = {
                    onClickRecipeCard(recipe.id)
                }
            )
        }
        if(isLoading){
            item{
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    CircularProgressIndicator()
                }
            }
        }
        if(loadError){
            item{
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
    }
}

@ExperimentalMaterialApi
@Composable
fun Dependency.View.DiscoveryView(
    router: RouterController
)
{
    val discoveryViewModel: DiscoveryViewModel = viewModel.discoveryViewModel()
    val uiState = discoveryViewModel.uiState.collectAsState().value
    val recipeList = uiState.recipeList
    val lazyListState = uiState.lazyListState
    val isLoading = uiState.isLoading
    val loadError = uiState.loadError
    val checkIfNewPageIsNeeded = discoveryViewModel.checkIfNewPageIsNeeded
    DiscoveryView(
        recipeList = recipeList,
        lazyListState = lazyListState,
        isLoading = isLoading,
        loadError = loadError,
        checkIfNewPageIsNeeded = checkIfNewPageIsNeeded,
        onClickRecipeCard = {recipeId: Int ->
            router.navigateToRecipeDetailView(recipeId)
        },
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewDiscoveryView()
{
    DiscoveryView(
        recipeList = listOf(
            RecipeDTO(
            id = 1,
            title = "test",
            featuredImage = "test",
                ingredients = listOf("string 1", "string 2")
            )),
        lazyListState = LazyListState(),
        isLoading = false,
        loadError = false,
        checkIfNewPageIsNeeded = { },
        onClickRecipeCard = { },
    )
}