package com.andreisingeleytsev.sportgameapp.ui.screens.football_screen

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
import androidx.compose.ui.tooling.preview.Preview
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
fun FootballScreen(navController: NavHostController, viewModel: FootballScreenViewModel = hiltViewModel()){
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
                            popUpTo(Routes.FOOTBALL_SCREEN) {
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

                        viewModel.onEvent(FootballScreenEvent.OnRotateChange(event.x, event.y))
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        viewModel.onEvent(FootballScreenEvent.OnPushTheBall)
                        true
                    }

                    else -> false
                }
            }
        ,
        contentAlignment = Alignment.TopCenter
    ) {
        val height = this.maxHeight
        val width = this.maxWidth
        viewModel.height = height
        viewModel.width = width
        Image(
            painter = painterResource(id = R.drawable.football_field),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(1250.dp)
        )
        val offset = 0.dp
        val fieldWidth= 360*height/838
        val platformAmpl = minOf(70*fieldWidth/360, 70.dp)
        viewModel.platformAmpl = platformAmpl
        val platformSize = minOf(fieldWidth/6, 60.dp)
        viewModel.platformSize = platformSize
        Box(
            modifier = Modifier
                .height(6.dp)
                .width(platformSize)
                .offset(
                    viewModel.platformOffsetX.value.dp,
                    viewModel.platformOffsetY.value

                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.platform),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }




        viewModel.ballSize.value = minOf(47*height/838, 47.dp)
        Image(
            painter = painterResource(id = R.drawable.ball), contentDescription = null,
            modifier = Modifier
                .size(viewModel.ballSize.value)
                .offset(
                    x = viewModel.ballOffsetX.value,
                    y = minOf(665 * height / 838, 665.dp) + viewModel.ballOffsetY.value
                )
        )

        val density = LocalContext.current.resources.displayMetrics.density
        val kx = density*(width/2).value
        val ky = minOf(665*height/838, 665.dp).value * density
        viewModel.centerWidthFloatY = ky
        viewModel.centerWidthFloatX = kx

        val arrowSizeX = minOf(22*width/360, 22.dp)
        val arrowSizeY = minOf(113*height/800, 113.dp)
        val arrowBottomPadding = minOf(149*height/838, 149.dp)
        val arrowOffsetRadius = minOf(54*height/838, 54.dp)
        viewModel.arrowRadius = arrowOffsetRadius
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Image(painter = painterResource(id = R.drawable.arrow), contentDescription = null,
                modifier = Modifier
                    .size(width = arrowSizeX, height = arrowSizeY)
                    .offset(
                        x = 0.dp,
                        y = -arrowBottomPadding
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
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(FootballScreenEvent.OnPause)
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
        if (viewModel.isSettingsOpen.value) FootballPauseMenu(width = 8*maxWidth/9)
    }
}

@Composable
fun FootballPauseMenu(width: Dp, viewModel: FootballScreenViewModel = hiltViewModel()) {
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
                          viewModel.onEvent(FootballScreenEvent.ToSettings)
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
                    viewModel.onEvent(FootballScreenEvent.OnContinue) },
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
                    viewModel.onEvent(FootballScreenEvent.ToMenu)},
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