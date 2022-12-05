package com.sample.androidtvcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardComponent(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    text: String,
    onFocusChanged: (FocusState) -> Unit
) {
    var focused by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .background(color = Color.White)
            .width(160.dp)
            .height(90.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                focused = it.isFocused
                onFocusChanged(it)
            }
            .focusable()
    ) {
        Text(
            text = text,
            color = if (focused) Color.Red else Color.Black,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun CardComponentPreview() {
    CardComponent(
        text = "1",
        onFocusChanged = {}
    )
}