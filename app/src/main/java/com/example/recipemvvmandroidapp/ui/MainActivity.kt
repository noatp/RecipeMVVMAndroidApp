package com.example.recipemvvmandroidapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.HomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var router: RouterController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tabController = rememberNavController()
            val modalController = rememberNavController()
            router = RouterController(
                tabController = tabController,
                modalController = modalController
            )
            HomeView(router = router)
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

