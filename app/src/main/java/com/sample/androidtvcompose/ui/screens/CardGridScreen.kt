package com.sample.androidtvcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CardGridScreen(
    items: List<Int> = emptyList()
) {
    val lazyGridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        state = lazyGridState,
        modifier = Modifier.padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = items
        ) { index, item->
            CardComponent(
                text = item.toString(),
                onFocusChanged = { focusState ->
                    if (focusState.isFocused) {
                        scope.launch {
                            val visibleItemsInfo = lazyGridState.layoutInfo.visibleItemsInfo
                            val visibleSet = visibleItemsInfo.map { it.index }.toSet()
                            if (index == visibleItemsInfo.last().index) {
                                lazyGridState.scrollToItem(index)
                            } else if (visibleSet.contains(index) && index >= 5) {
                                lazyGridState.scrollToItem(index - 5)
                            }
                        }
                    }
                }
            )
        }
    }
}