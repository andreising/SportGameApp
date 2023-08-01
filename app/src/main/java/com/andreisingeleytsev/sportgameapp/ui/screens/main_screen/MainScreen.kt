package com.andreisingeleytsev.sportgameapp.ui.screens.main_screen

import android.app.Activity
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
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.theme.SecondaryColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents


@Composable
fun MainScreen(navController: NavHostController, viewModel: MainScreenViewModel = hiltViewModel()){
    viewModel.context = LocalContext.current as Activity?
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnNavigate -> {
                    navController.navigate(it.route)
                }
            }
        }
    }
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
        Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween) {
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
                .wrapContentSize()
                .padding(bottom = padding)){
                val state by remember {
                    viewModel.menuState
                }
                when(state) {
                    MAIN -> StartMenu()
                    LEVELS -> LevelMenu()
                    DIFFICULTY-> DifficultyMenu()
                }

            }
        }


    }
}

@Composable
fun StartMenu(viewModel: MainScreenViewModel = hiltViewModel()) {
    val context = LocalContext.current as Activity?
    Column() {
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnNavigateButtonPressed(LEVELS))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Start", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnNavigateButtonPressed(DIFFICULTY))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Levels", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnNavigateButtonPressed(SETTINGS))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Settings", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                context?.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Exit", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
    }
}

@Composable
fun LevelMenu(viewModel: MainScreenViewModel = hiltViewModel()){
    Column() {
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnLevelChoose(1))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Level 1", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnLevelChoose(2))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Level 2", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnLevelChoose(3))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Level 3", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnNavigateButtonPressed(MAIN))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Menu", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
    }
}

@Composable
fun DifficultyMenu(viewModel:MainScreenViewModel = hiltViewModel()){
    val color1 = if (viewModel.difficulty.value==1) SecondaryColor
    else Color.White
    val color2 = if (viewModel.difficulty.value==2) SecondaryColor
    else Color.White
    val color3 = if (viewModel.difficulty.value==3) SecondaryColor
    else Color.White
    val textColor1 = if (viewModel.difficulty.value==1) Color.White
    else MainColor
    val textColor2 = if (viewModel.difficulty.value==2) Color.White
    else MainColor
    val textColor3 = if (viewModel.difficulty.value==3) Color.White
    else MainColor
    Column() {
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(1))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = color1
            )
            Text(text = "Easy", style = TextStyle(
                color = textColor1,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(2))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = color2
            )
            Text(text = "Medium", style = TextStyle(
                color = textColor2,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnDifficultyChoose(3))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = color3
            )
            Text(text = "Hard", style = TextStyle(
                color = textColor3,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(MainScreenEvent.OnNavigateButtonPressed(MAIN))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "Menu", style = TextStyle(
                color = MainColor,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            ))
        }
    }
}
