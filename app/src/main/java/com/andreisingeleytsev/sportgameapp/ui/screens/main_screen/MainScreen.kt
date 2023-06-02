package com.andreisingeleytsev.sportgameapp.ui.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.buttonPath


@Preview
@Composable
fun MainScreen(){
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.TopCenter
    ) {
        val paddingPercentage = 0.07f
        val screenHeight = maxHeight
        val screenWidth = maxWidth
        val startEndPadding: Dp = screenWidth*0.083f
        val padding: Dp = (screenHeight * paddingPercentage)
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.size(width = screenWidth, height = screenHeight * 0.3f)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding)){
                StartMenu(startEndPadding)
            }
        }


    }
}

@Composable
fun StartMenu() {
    Button(modifier = Modifier.fillMaxWidth(), onClick = {

    }, shape =  GenericShape { size, _ ->
        buttonPath(size = size)
    }) {

    }
}





