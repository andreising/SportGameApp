package com.andreisingeleytsev.sportgameapp.ui.screens.result_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.theme.LoadColor
import com.andreisingeleytsev.sportgameapp.ui.theme.MainColor
import com.andreisingeleytsev.sportgameapp.ui.utils.Fonts

@Composable
fun ResultScreen(navController: NavHostController){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    val myData = sharedPreferences?.getInt("key", -1)
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxWidth()){
            Image(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.logo), contentDescription = null)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Your score: "+myData.toString(), style = TextStyle(
                color = Color.White,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.customFontFamily,
                fontSize = 40.sp
            )
            )
            IconButton(
                onClick = {
                    navController.popBackStack()
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
}