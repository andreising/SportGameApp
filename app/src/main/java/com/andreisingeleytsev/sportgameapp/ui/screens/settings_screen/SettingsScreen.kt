package com.andreisingeleytsev.sportgameapp.ui.screens.settings_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.DIFFICULTY
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.DifficultyMenu
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.LEVELS
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.LevelMenu
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.MAIN
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.MainScreenEvent
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.MainScreenViewModel
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.SETTINGS

import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.StartMenu
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents

@Composable
fun SettingsScreen(navController: NavHostController, mediaPlayer: MediaPlayer?) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.TopCenter
    ) {
        val paddingPercentage = 0.07f
        val screenHeight = maxHeight
        val screenWidth = maxWidth
        val padding: Dp = (screenHeight * paddingPercentage)
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.size(width = screenWidth, height = screenHeight * 0.3f)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = padding)
            ) {
                SettingsMenu(0.25 * screenHeight, mediaPlayer, navController)

            }

        }
    }
}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsMenu(padding: Dp, mediaPlayer: MediaPlayer?, navController: NavHostController) {
    var sound by remember {
        mutableStateOf(0f)
    }
    Column() {
        Column() {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Text(
                    text = "Sound:", style = TextStyle(
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontFamily = Fonts.customFontFamily,
                        fontSize = 40.sp
                    )
                )
//                BoxWithConstraints(modifier = Modifier
//                    .fillMaxWidth()
//                    .pointerInteropFilter { event ->
//                        when (event.action) {
//                            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
//                                sound = event.x
//                                true
//                            }
//
//                            else -> false
//                        }
//                    }) {
//                    Icon(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(30.dp),
//                        painter = painterResource(id = R.drawable.soundbar),
//                        contentDescription = null,
//                        tint = Color.White
//                    )
//                    Icon(
//                        modifier = Modifier
//                            .width(100f.dp)
//                            .height(30.dp),
//                        painter = painterResource(id = R.drawable.soundbar),
//                        contentDescription = null,
//                        tint = Color.Green
//                    )
//
//                }
            }
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)) {
                Text(
                    text = "Off_On", style = TextStyle(
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontFamily = Fonts.customFontFamily,
                        fontSize = 40.sp
                    )
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(30.dp)
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val context = LocalContext.current
                    val sharedPreferences = LocalContext.current.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                    val mP = MediaPlayer.create(context, R.raw.music)
                    IconToggleButton( checked = true,
                        onCheckedChange = {
                            mP?.start()
                            mP?.isLooping = true
                                sharedPreferences?.edit()?.putBoolean("music", true)?.apply()
                        })
                    {
                        Image(
                            painter = painterResource(R.drawable.selected),
                            contentDescription = "Radio button icon",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconToggleButton( checked = true,
                        onCheckedChange = {
                            mP?.stop()
                            mediaPlayer?.stop()
                            sharedPreferences?.edit()?.putBoolean("music", false)?.apply()
                        })
                    {
                        Icon(
                            painter = painterResource(R.drawable.selected),
                            contentDescription = "Radio button icon",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }
                }
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = padding)
                        .height(60.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.button),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(text = "Back", style = TextStyle(
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
}
