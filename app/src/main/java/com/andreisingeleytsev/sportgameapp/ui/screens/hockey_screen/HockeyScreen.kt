package com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen

import android.app.Activity
import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.theme.HockeyBackground
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HockeyScreen(navController:NavHostController, viewModel: HockeyScreenViewModel = hiltViewModel()) {
    viewModel.context = LocalContext.current as Activity?
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnNavigate -> {
                    if (it.route == Routes.SETTINGS_SCREEN){
                        navController.navigate(it.route){

                        }
                    }
                    else{
                        navController.navigate(it.route){
                            popUpTo(Routes.HOCKEY_SCREEN) {
                                inclusive = true
                            }
                        }
                    }

                }
            }
        }
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(HockeyBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        val height = this.maxHeight
        val width = this.maxWidth
        val topPadding = 0.03*height
        viewModel.topPadding = topPadding
        val washerSize = 0.1*width

        Box() {
            Image(
                modifier = Modifier.size(width = 360.dp, height = 720.dp),
                painter = painterResource(id = R.drawable.hockey_background),
                contentDescription = null
            )
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    ,
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hockey_field),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(720.dp)
                        .padding(top = topPadding)
                )

            }
            val platformSize = minOf(width/6, 60.dp)
            viewModel.platformSize = platformSize
            val platformOffset = minOf(height.value.toInt()*60/800, 720*60/800).dp
            viewModel.platformXStart = width / 2 - platformSize / 2 + width / 170 - 90.dp
            viewModel.platformOffsetY.value = platformOffset
            val off = remember {
                viewModel.platformOffsetX
            }
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(platformSize)
                    .offset(
                        width / 2 - platformSize / 2 + width / 170 - 90.dp + off.value.dp,
                        viewModel.platformOffsetY.value
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.platform),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            val offsetY = 0.767
            val fieldHeight = minOf(height.value.toInt()*offsetY, offsetY*720)
            viewModel.washerSize = washerSize
            viewModel.defaultWasherHeight = fieldHeight.toInt()
            viewModel.washerXPosition = width / 2 - washerSize / 2 + width / 170
            Box(
                modifier = Modifier
                    .size(washerSize)
                    .offset(
                        width / 2 - washerSize / 2 + width / 170,
                        fieldHeight.dp - washerSize / 2 - viewModel.washerOffset.value.dp
                    )
                    .pointerInteropFilter { event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                                viewModel.onEvent(HockeyScreenEvent.OnTouch)
                                true
                            }

                            else -> false
                        }
                    }
            ) {
                Image(painter = painterResource(id = R.drawable.washer),
                    contentDescription = null,
                modifier = Modifier.fillMaxSize())
            }
        }


//

    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(HockeyScreenEvent.OnPause)
            },
            modifier = Modifier
                .padding(start = maxWidth / 24, top = maxWidth / 24)
                .size(maxWidth / 12)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = null,
                tint = MainColor,
                modifier = Modifier.fillMaxSize()
            )
        }
        
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Score:" + viewModel.score.value.toString(),
            modifier = Modifier.padding(bottom = maxWidth / 2),
            style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            )
        )
        if (viewModel.isSettingsOpen.value) HockeyPauseMenu(width = 8*maxWidth/9)
    }
}

@Composable
fun HockeyPauseMenu(width: Dp, viewModel: HockeyScreenViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier.wrapContentSize(),
        border = BorderStroke(4.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MainColor
        )
    ) {
        Column(
            modifier = Modifier
                .width(width)
                .padding(30.dp)
                .background(MainColor)
        ) {
            Button(
                onClick = { viewModel.onEvent(HockeyScreenEvent.ToSettings) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Settings",
                    style = TextStyle(
                        color = MainColor,
                        fontStyle = FontStyle.Italic,
                        fontFamily = Fonts.customFontFamily,
                        fontSize = 40.sp
                    )
                )
            }
            Button(
                onClick = {viewModel.onEvent(HockeyScreenEvent.OnContinue) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Back",
                    style = TextStyle(
                        color = MainColor,
                        fontStyle = FontStyle.Italic,
                        fontFamily = Fonts.customFontFamily,
                        fontSize = 40.sp
                    )
                )
            }
            Button(
                onClick = { viewModel.onEvent(HockeyScreenEvent.ToMenu) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Menu",
                    style = TextStyle(
                        color = MainColor,
                        fontStyle = FontStyle.Italic,
                        fontFamily = Fonts.customFontFamily,
                        fontSize = 40.sp
                    )
                )
            }
        }
    }

}