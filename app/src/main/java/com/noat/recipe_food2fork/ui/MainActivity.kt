package com.noat.recipe_food2fork.ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.noat.recipe_food2fork.data.local.RecipeLocalDatabase
import com.noat.recipe_food2fork.data.remote.RecipeNetworkService
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.ui.router.RouterController
import com.noat.recipe_food2fork.ui.view.HomeView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class ThisApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ThisApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

//    override fun onCreate() {
//        super.onCreate()
//        // initialize for any
//
//        // Use ApplicationContext.
//        // example: SharedPreferences etc...
////        val context: Context = ThisApplication.applicationContext()
//    }
}

object Singleton{
    private val recipeNetworkService: RecipeNetworkService = RecipeNetworkService()
    private val recipeLocalDatabase: RecipeLocalDatabase = RecipeLocalDatabase(ThisApplication.applicationContext())
    val appDependency= Dependency(
        recipeNetworkService = recipeNetworkService,
        recipeLocalDatabase = recipeLocalDatabase
    )
}

class MainActivity : ComponentActivity() {
    private val dependency: Dependency = Singleton.appDependency
    private lateinit var router: RouterController
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tabController = rememberNavController()
            val modalController = rememberNavController()
            router = RouterController(
                tabController = tabController,
                modalController = modalController
            )
            dependency.view().HomeView(router = router)
        }
    }
    override fun onBackPressed() {
        if(router.popOffModal())
        {
            return
        }
        super.onBackPressed()
    }
}
