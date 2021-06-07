package com.noat.recipe_food2fork.dependency

import com.noat.recipe_food2fork.data.remote.RecipeNetworkService

class Dependency (
    val recipeService: com.noat.recipe_food2fork.data.remote.RecipeNetworkService,
) {
    class Service(val dependency: Dependency) // aka data sources
    private fun service(): Service
    {
        return Service(this)
    }

    class Repository(val service: Service)
    private fun repository(): Repository
    {
        return Repository(service())
    }

    class UseCase(val repository: Repository)
    private fun useCase(): UseCase
    {
        return UseCase(repository())
    }

    class ViewModel(val useCase: UseCase)
    private fun viewModel(): ViewModel
    {
        return ViewModel(useCase())
    }

    class View(val viewModel: ViewModel)
    fun view(): View
    {
        return View(viewModel())
    }
}

fun Dependency.Service.recipeService(): com.noat.recipe_food2fork.data.remote.RecipeNetworkService {
    return dependency.recipeService
}