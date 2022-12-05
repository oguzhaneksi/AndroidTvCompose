package com.sample.androidtvcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sample.androidtvcompose.data.model.enums.DisplayType
import com.sample.androidtvcompose.ui.screens.ContentScreen


fun NavGraphBuilder.menuNavGraph(
    route: String,
    displayType: DisplayType,
    listGroupId: Int = 0,
    listId: Int = 0
) {
    navigation(
        startDestination = Screen.MenuPage(route).route,
        route = route
    ) {
        composable(
            route = Screen.MenuPage(route).route,
            arguments = listOf(
                navArgument(ARG_LIST_GROUP_ID) {
                    type = NavType.IntType
                    defaultValue = listGroupId
                },
                navArgument(ARG_LIST_ID) {
                    type = NavType.IntType
                    defaultValue = listId
                }
            )
        ) {
            val items = (1..40).toList()
            when (displayType) {
                DisplayType.CardRows -> {
                    ContentScreen(items = items)
                }
                DisplayType.CardGrid -> {
                    ContentScreen(items = items)
                }
            }
        }
    }
}