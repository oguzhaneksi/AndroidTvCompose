package com.sample.androidtvcompose.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CardRowsScreen(
    items: List<Pair<String, List<Int>>> = emptyList(),
    sliders: List<Int> = emptyList()
) {
    val lazyColumnState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val onFocusChanged: (Int) -> Unit = { index ->
        scope.launch {
            val visibleItemsInfo = lazyColumnState.layoutInfo.visibleItemsInfo
            val visibleSet = visibleItemsInfo.map { it.index }.toSet()
            if (index == visibleItemsInfo.last().index) {
                lazyColumnState.scrollToItem(index)
            }
            else if (visibleSet.contains(index) && index > 1) {
                lazyColumnState.animateScrollToItem(index - 1)
            }
        }
    }
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SliderRow(
                items = sliders,
                onFocusChanged = { state ->
                    if (state.hasFocus) {
                        scope.launch {
                            handleFocus(0, lazyColumnState)
                        }
                    }
                }
            )
        }
        itemsIndexed(
            items = items
        ) { index, row->
            CardRow(
                title = row.first,
                items = row.second,
                onFocusChanged = { state ->
                    if (state.hasFocus) {
                        scope.launch {
                            handleFocus(index + 1, lazyColumnState)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CardRow(
    title: String,
    items: List<Int> = emptyList(),
    onFocusChanged: (FocusState) -> Unit
) {
    val lazyRowState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyRowState
        ) {
            itemsIndexed(
                items = items
            ) { index, item ->
                CardComponent(
                    text = item.toString(),
                    onFocusChanged = { focusState ->
                        onFocusChanged(focusState)
                        if (focusState.isFocused) {
                            scope.launch {
                                handleFocus(index, lazyRowState)
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SliderRow(
    items: List<Int> = emptyList(),
    onFocusChanged: (FocusState) -> Unit
) {
    val lazyRowState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            state = lazyRowState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyRowState)
        ) {
            itemsIndexed(
                items = items
            ) { index, item ->
                SliderComponent(
                    title = item.toString(),
                    onClick = {},
                    onFocusChanged = { focusState ->
                        onFocusChanged(focusState)
                        if (focusState.isFocused) {
                            scope.launch {
                                handleFocus(index, lazyRowState)
                            }
                        }
                    }
                )
            }
        }
    }
}

private suspend fun handleFocus(index: Int, state: LazyListState) {
    val visibleItemsInfo = state.layoutInfo.visibleItemsInfo
    val visibleSet = visibleItemsInfo.map { it.index }.toSet()
    if (index == visibleItemsInfo.last().index) {
        state.scrollBy((visibleItemsInfo.last().size - (state.layoutInfo.viewportEndOffset - visibleItemsInfo.last().offset)).toFloat())
    }
    else if (index == visibleItemsInfo.first().index) {
        state.scrollBy((visibleItemsInfo.first().offset - state.layoutInfo.viewportStartOffset).toFloat())
    }
    else if (!visibleSet.contains(index) && index > visibleItemsInfo.last().index) {
        val completelyVisibleItemCount = if (visibleItemsInfo.first().offset < 0) visibleSet.size - 1 else visibleSet.size
        state.scrollToItem(index - completelyVisibleItemCount + 1)
    }
    else if (!visibleSet.contains(index) && index < visibleItemsInfo.first().index) {
        state.scrollToItem(index)
    }
}

@Preview
@Composable
fun CardRowsScreenPreview() {
    val items = (1..10).map {
        "Row $it" to (1..20).toList()
    }
    CardRowsScreen(
        items = items
    )
}