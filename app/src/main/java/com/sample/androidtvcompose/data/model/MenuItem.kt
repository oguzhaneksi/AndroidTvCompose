package com.sample.androidtvcompose.data.model

import com.sample.androidtvcompose.data.model.enums.DisplayType

data class MenuItem(
    val id: Int,
    val icon: Int,
    val title: String,
    val displayType: DisplayType,
    val listId: Int? = null,
    val listGroupId: Int? = null,
    val route: String
)