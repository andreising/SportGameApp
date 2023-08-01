package com.andreisingeleytsev.sportgameapp.ui.screens.rules_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.theme.GreenBackground
import com.andreisingeleytsev.sportgameapp.ui.theme.HockeyBackground
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents

@Composable
fun RulesScreen(state: MutableState<Boolean?>, viewModel: RulesScreenViewModel = hiltViewModel()){
    val position by remember {
        viewModel.position
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnNavigate -> {
                    state.value = false
                }
            }
        }
    }
    val color = when (position) {
        1 -> GreenBackground
        2 -> HockeyBackground
        3 -> GreenBackground
        else -> GreenBackground
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = color), contentAlignment = Alignment.BottomCenter){
        val currentPicture = when (position) {
            1 -> R.drawable.rules_football
            2 -> R.drawable.rules_hockey
            3 -> R.drawable.rules_golf
            else -> R.drawable.rules_football
        }

        Image(painter = painterResource(id = currentPicture), contentDescription = null,
        modifier = Modifier.fillMaxSize())
        Row(modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                viewModel.onEvent(RulesScreenEvent.OnBackButtonPressed)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MainColor
            )) {
                Text(text = "Back", style = TextStyle(
                color = Color.White,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
                )
                )
            }
            Button(onClick = {
                viewModel.onEvent(RulesScreenEvent.OnNextButtonPressed)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MainColor
            )) {
                Text(text = "Next", style = TextStyle(
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontFamily = Fonts.customFontFamily,
                    fontSize = 40.sp
                )
                )
            }
        }
    }

    val currentScore = when(position){
        1 -> "Сontrol the ball and score a goal!"
        2 -> "Choose the moment and shoot the puck into the gate!"
        3-> "Hit the ball in all the holes!"
        else -> ""
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(modifier = Modifier.padding(30.dp), text = currentScore, style = TextStyle(
            color = MainColor,
            fontStyle = FontStyle.Italic,
            fontFamily = Fonts.customFontFamily,
            fontSize = 40.sp
        ), textAlign = TextAlign.Center)
    }
}