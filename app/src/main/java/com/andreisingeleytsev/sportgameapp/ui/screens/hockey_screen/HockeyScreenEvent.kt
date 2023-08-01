package com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen

import com.andreisingeleytsev.sportgameapp.ui.screens.football_screen.FootballScreenEvent

sealed class HockeyScreenEvent(){
    object OnTouch: HockeyScreenEvent()
    object OnPause: HockeyScreenEvent()
    object ToSettings: HockeyScreenEvent()
    object OnContinue: HockeyScreenEvent()
    object ToMenu: HockeyScreenEvent()

}
