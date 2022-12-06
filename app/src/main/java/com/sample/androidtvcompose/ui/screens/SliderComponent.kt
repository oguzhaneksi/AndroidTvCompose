package com.sample.androidtvcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SliderComponent(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    onFocusChanged: (FocusState) -> Unit
) {
    var focused by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .width(720.dp)
            .aspectRatio(16 / 9f)
            .background(Color.White)
    ) {
        Text(
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentSize()
                .onFocusChanged {
                    focused = it.isFocused
                    onFocusChanged(it)
                }
                .focusable(),
            enabled = focused,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (focused) Color.Red else Color.White
            )
        ) {
            Text(
                text = "Button",
                color = if (focused) Color.White else Color.Black
            )
        }
    }
}

@Preview
@Composable
fun SliderComponentPreview() {
    SliderComponent(title = "1", onClick = {}, onFocusChanged = {})
}