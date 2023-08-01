package com.andreisingeleytsev.sportgameapp.ui.activity

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.andreisingeleytsev.sportgameapp.R
import com.andreisingeleytsev.sportgameapp.ui.navigation.SportGameMainNavigation
import com.andreisingeleytsev.sportgameapp.ui.screens.football_screen.FootballScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.golf_screen.GolfScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen.HockeyScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.MainScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.rules_screen.RulesScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.splash_screen.SplashScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.splash_screen.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.context = this.application
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        setContent {
            if (viewModel.isFirstLaunch.value == null) SplashScreen()
            if (viewModel.isFirstLaunch.value == false) SportGameMainNavigation(mediaPlayer)
            if (viewModel.isFirstLaunch.value == true) RulesScreen(viewModel.isFirstLaunch)
        }
    }



    override fun onResume() {
        super.onResume()
        val sharedPreferences = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val music = sharedPreferences?.getBoolean("music", true)
        if (music==true) {

            mediaPlayer?.start()
            mediaPlayer?.isLooping = true
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
