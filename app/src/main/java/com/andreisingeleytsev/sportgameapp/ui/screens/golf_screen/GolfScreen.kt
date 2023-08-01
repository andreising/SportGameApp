package com.andreisingeleytsev.sportgameapp.ui.screens.golf_screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
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
import com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen.HockeyPauseMenu
import com.andreisingeleytsev.sportgameapp.ui.theme.GreenBackground
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GolfScreen(navController: NavHostController, viewModel: GolfScreenViewModel = hiltViewModel()){
    viewModel.context = LocalContext.current as Activity?
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnNavigate -> {
                    if (it.route == Routes.SETTINGS_SCREEN){
                        navController.navigate(it.route)
                    }
                    else{
                        navController.navigate(it.route){
                            popUpTo(Routes.GOLF_SCREEN) {
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
            .background(GreenBackground)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        viewModel.onEvent(GolfScreenEvent.OnTouch)
                        true
                    }

                    else -> false
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        val height = this.maxHeight
        val width = this.maxWidth
        viewModel.width = width
        viewModel.height = height
        Image(
            painter = painterResource(id = R.drawable.golf_field),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            val thingSize = minOf(0.075 * width, 30.dp)
            val thingOffsetY = minOf(7 * height / 8, 700.dp)
            Image(
                painter = painterResource(id = R.drawable.golf_thing), contentDescription = null,
                modifier = Modifier
                    .size(thingSize)
                    .offset(x = width / 2 - thingSize / 2, y = thingOffsetY)
            )

            val ballSize = 0.828*thingSize
            viewModel.ballSize = ballSize
            viewModel.ballStartX = width / 2 - ballSize / 2
            viewModel.ballStartY = thingOffsetY - ballSize
            Image(painter = painterResource(id = R.drawable.golf_ball), contentDescription = null,
                modifier = Modifier
                    .size(ballSize)
                    .offset(
                        x = width / 2 - ballSize / 2 - viewModel.ballXOffset.value,
                        y = thingOffsetY - ballSize - viewModel.ballYOffset.value
                    ))
            val arrowSizeX = minOf(22*width/360, 22.dp)
            val arrowSizeY = minOf(113*height/800, 113.dp)

            val arrowOffsetRadius = minOf(32*height/800, 32.dp)
            viewModel.arrowRadius = arrowOffsetRadius

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                Image(painter = painterResource(id = R.drawable.arrow), contentDescription = null,
                    modifier = Modifier
                        .size(width = arrowSizeX, height = arrowSizeY)
                        .offset(
                            x = viewModel.arrowOffsetX.value,
                            y = -minOf(98 * height / 800, 98.dp) - viewModel.arrowOffsetY.value
                        )
                        .align(Alignment.BottomCenter)
                        .drawWithContent {
                            drawIntoCanvas { canvas ->
                                canvas.nativeCanvas.rotate(
                                    viewModel.arrowRotate.value,
                                    size.width / 2f,
                                    size.height
                                )
                                drawContent()
                            }
                        }
                        )
            }



        }



    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(GolfScreenEvent.OnPause)
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
            text = "Score: " + viewModel.score.value.toString(),
            modifier = Modifier.padding(top = 30.dp),
            style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            )
        )
        if (viewModel.isSettingsOpen.value) GolfPauseMenu(width = 8*maxWidth/9)
    }
}

@Composable
fun GolfPauseMenu(width: Dp, viewModel: GolfScreenViewModel = hiltViewModel()) {
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
                onClick = {
                    viewModel.onEvent(GolfScreenEvent.ToSettings)
                },
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
                onClick = {
                    viewModel.onEvent(GolfScreenEvent.OnContinue) },
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
                onClick = {
                    viewModel.onEvent(GolfScreenEvent.ToMenu)},
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