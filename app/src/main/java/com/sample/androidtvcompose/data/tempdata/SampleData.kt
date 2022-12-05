package com.sample.androidtvcompose.data.tempdata

import com.sample.androidtvcompose.R
import com.sample.androidtvcompose.data.model.MenuItem
import com.sample.androidtvcompose.data.model.enums.DisplayType

object SampleData {
    val menuItems = listOf(
        MenuItem(
            id = 1,
            icon = R.drawable.ic_round_home_24,
            title = "Home",
            displayType = DisplayType.CardRows,
            listGroupId = 1,
            route = "home"
        ),
        MenuItem(
            id = 2,
            icon = R.drawable.ic_baseline_explore_24,
            title = "Explore",
            displayType = DisplayType.CardGrid,
            listId = 11,
            route = "explore"
        ),
        MenuItem(
            id = 3,
            icon = R.drawable.ic_baseline_live_tv_24,
            title = "Live TV",
            displayType = DisplayType.CardRows,
            listGroupId = 2,
            route = "livetv"
        )
    )
}