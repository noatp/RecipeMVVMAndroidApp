package com.example.recipemvvmandroidapp.view.viewComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.recipemvvmandroidapp.router.TabViewDestination
import com.example.recipemvvmandroidapp.ui.theme.*

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    tabs: Array<TabViewDestination>,
    tabSelected: TabViewDestination,
    onTabSelected: (TabViewDestination) -> Unit
){
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier.fillMaxSize(),
        indicator = @Composable { tabPositions ->
            Box(
                modifier
                    .tabIndicatorOffset(tabPositions[tabSelected.ordinal])
                    .fillMaxSize()
                    .background(color = DarkBackground.copy(alpha = 0.2f)),
                content = {}
            )
        },
    ) {
        tabs.forEachIndexed{ index, tab ->
            Tab(
                selected = index == tabSelected.ordinal,
                onClick = {onTabSelected(tab)},
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(),
                selectedContentColor = DarkBackground,
                unselectedContentColor = DarkText,
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = ""
                        )
                        Text(
                            text = tab.name,
                            maxLines = 1
                        )
                    }
                }
            )
        }
    }
}


@Preview
@Composable
fun PreviewBottomNavBar()
{
    BottomNavBar(
        tabs = TabViewDestination.values(),
        tabSelected =  TabViewDestination.Search,
        onTabSelected = {}
    )
}