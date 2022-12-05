package com.sample.androidtvcompose.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sample.androidtvcompose.data.model.enums.DisplayType
import com.sample.androidtvcompose.ui.screens.CardGridScreen
import com.sample.androidtvcompose.ui.screens.CardRowsScreen


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

            when (displayType) {
                DisplayType.CardRows -> {
                    val items = (1..10).map {
                        "Row $it" to (1..20).toList()
                    }
                    CardRowsScreen(items = items)
                }
                DisplayType.CardGrid -> {
                    val items = (1..40).toList()
                    CardGridScreen(items = items)
                }
            }
        }
    }
}