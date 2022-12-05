package com.sample.androidtvcompose.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sample.androidtvcompose.data.model.MenuItem
import com.sample.androidtvcompose.data.tempdata.SampleData
import com.sample.androidtvcompose.ui.navigation.Screen
import com.sample.androidtvcompose.ui.navigation.createPath

@Composable
fun SideMenuComponent(
    modifier: Modifier = Modifier,
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
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(animatedWidth)
            .background(Color.Gray)
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
                MenuItem(
                    item = item,
                    expanded = expanded,
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
    onFocusChanged: (FocusState) -> Unit,
    onItemClick: () -> Unit
) {
    var focused by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .wrapContentWidth()
            .clickable(enabled = focused) {
                onItemClick()
            }
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
            tint = if (focused) Color.Red else Color.White
        )
        AnimatedVisibility(
            visible = expanded
        ) {
            Text(
                text = item.title,
                color = if (focused) Color.Red else Color.White,
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
        navController = rememberNavController()
    )
}