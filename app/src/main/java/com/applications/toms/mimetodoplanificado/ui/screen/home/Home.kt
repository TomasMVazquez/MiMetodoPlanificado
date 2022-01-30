package com.applications.toms.mimetodoplanificado.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Home(){
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Yellow)) {

        Text(text = "HOME")
    }
}