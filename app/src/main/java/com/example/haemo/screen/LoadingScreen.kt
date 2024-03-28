package com.example.haemo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.haemo.R

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Image(painter = painterResource(id = R.drawable.wont_icon), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.wont), contentDescription = "")
        }
    }
}