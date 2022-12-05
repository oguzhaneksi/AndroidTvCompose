package com.sample.androidtvcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sample.androidtvcompose.data.model.MenuItem
import com.sample.androidtvcompose.data.tempdata.SampleData

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    pages: List<MenuItem> = emptyList(),
    navHostController: NavHostController
) {

    if (pages.isNotEmpty()) {
        val homeRoute = pages[0].route
        val homeListGroupId = pages[0].listGroupId
        val homeListId = pages[0].listId

        NavHost(
            navController = navHostController,
            startDestination = homeRoute
        ) {
            pages.forEach { page->
                menuNavGraph(
                    route = page.route,
                    displayType = page.displayType
                )
            }
        }
    }
}