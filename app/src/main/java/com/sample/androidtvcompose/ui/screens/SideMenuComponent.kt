package com.sample.androidtvcompose.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.androidtvcompose.data.model.MenuItem
import com.sample.androidtvcompose.data.tempdata.SampleData
import com.sample.androidtvcompose.ui.navigation.Screen
import com.sample.androidtvcompose.ui.navigation.createPath

@Composable
fun SideMenuComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    selectedIconColor: Color,
    unselectedIconColor: Color,
    focusedIconColor: Color,
    navController: NavController,
    menuItems: List<MenuItem>
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val menuWidth = if (expanded) 160.dp else 80.dp
    val onFocusChanged: (FocusState) -> Unit = {
        expanded = it.hasFocus
    }
    val animatedWidth by animateDpAsState(
        targetValue = menuWidth,
        animationSpec = tween(durationMillis = 500)
    )
    val focusRequester = FocusRequester()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(animatedWidth)
            .background(backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    vertical = 48.dp,
                    horizontal = 16.dp
                )
        ) {
            itemsIndexed(
                items = menuItems
            ) { index, item ->
                val selected = navBackStackEntry?.destination?.hierarchy?.any {
                    it.route == item.route
                } == true
                if (index == 0) {
                    DisposableEffect(
                        key1 = Unit,
                    ) {
                        focusRequester.requestFocus()
                        onDispose {  }
                    }
                }
                MenuItem(
                    item = item,
                    expanded = expanded,
                    selected = selected,
                    selectedItemColor = selectedIconColor,
                    focusedItemColor = focusedIconColor,
                    focusRequester = focusRequester,
                    defaultItemColor = unselectedIconColor,
                    onFocusChanged = onFocusChanged,
                    onItemClick = {
                        navController.navigate(Screen.MenuPage(item.route).createPath(
                            item.listGroupId ?: 0,
                            item.listId ?: 0
                        ))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    item: MenuItem,
    expanded: Boolean,
    selected: Boolean,
    selectedItemColor: Color,
    defaultItemColor: Color,
    focusedItemColor: Color,
    focusRequester: FocusRequester,
    onFocusChanged: (FocusState) -> Unit,
    onItemClick: () -> Unit
) {
    var focused by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .wrapContentWidth()
            .selectable(
                selected = selected,
                enabled = focused,
                onClick = {
                    onItemClick()
                }
            )
            .focusRequester(focusRequester)
            .onFocusChanged {
                focused = it.isFocused
                onFocusChanged(it)
            }
            .focusable(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            tint = if (focused) focusedItemColor else if (selected) selectedItemColor else defaultItemColor
        )
        AnimatedVisibility(
            visible = expanded,
            enter = expandHorizontally(animationSpec = tween(durationMillis = 500))
        ) {
            Text(
                text = item.title,
                color = if (focused) focusedItemColor else if (selected) selectedItemColor else defaultItemColor,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Preview
@Composable
fun SideMenuComponentPreview() {
    SideMenuComponent(
        menuItems = SampleData.menuItems,
        navController = rememberNavController(),
        backgroundColor = Color.Gray,
        selectedIconColor = Color.Red,
        unselectedIconColor = Color.Black,
        focusedIconColor = Color.Red
    )
}