package com.andreisingeleytsev.sportgameapp.ui.navigation

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.sportgameapp.ui.screens.football_screen.FootballScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.golf_screen.GolfScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen.HockeyScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.main_screen.MainScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.result_screen.ResultScreen
import com.andreisingeleytsev.sportgameapp.ui.screens.settings_screen.SettingsMenu
import com.andreisingeleytsev.sportgameapp.ui.screens.settings_screen.SettingsScreen
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes

@Composable
fun SportGameMainNavigation(mediaPlayer: MediaPlayer?) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)
        }
        composable(Routes.FOOTBALL_SCREEN) {
            FootballScreen(navController)
        }
        composable(Routes.HOCKEY_SCREEN) {
            HockeyScreen(navController)
        }
        composable(Routes.GOLF_SCREEN) {
            GolfScreen(navController)
        }
        composable(Routes.RESULT_SCREEN) {
            ResultScreen(navController)
        }
        composable(Routes.SETTINGS_SCREEN) {
            SettingsScreen(navController, mediaPlayer)
        }

    }
}