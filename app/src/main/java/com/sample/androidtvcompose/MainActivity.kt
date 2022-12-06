package com.sample.androidtvcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sample.androidtvcompose.data.tempdata.SampleData
import com.sample.androidtvcompose.ui.navigation.Navigation
import com.sample.androidtvcompose.ui.screens.SideMenuComponent
import com.sample.androidtvcompose.ui.theme.AndroidTvComposeTheme

val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTvComposeTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(
                    values = arrayOf(LocalNavController provides navController)
                ) {
                    val pages = SampleData.menuItems
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        SideMenuComponent(
                            menuItems = pages,
                            navController = navController,
                            backgroundColor = Color.Gray,
                            selectedIconColor = Color.Red,
                            unselectedIconColor = Color.Black,
                            focusedIconColor = Color.Red
                        )
                        Navigation(
                            navHostController = navController,
                            pages = pages
                        )
                    }
                }
            }
        }
    }
}