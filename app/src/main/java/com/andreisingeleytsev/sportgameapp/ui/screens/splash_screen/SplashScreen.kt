package com.andreisingeleytsev.sportgameapp.ui.screens.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.theme.LoadColor
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor

@Preview
@Composable
fun SplashScreen() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.Center
    ) {
        val paddingPercentage = 0.1f
        val screenHeight = maxHeight
        val padding: Dp = (screenHeight * paddingPercentage)
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxWidth()){
            Image(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.logo), contentDescription = null)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp).padding(bottom = padding),
                color = LoadColor
            )
        }
    }
}