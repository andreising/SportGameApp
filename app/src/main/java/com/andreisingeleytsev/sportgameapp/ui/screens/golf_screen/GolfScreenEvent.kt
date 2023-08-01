package com.andreisingeleytsev.sportgameapp.ui.screens.golf_screen

import com.andreisingeleytsev.sportgameapp.ui.screens.football_screen.FootballScreenEvent

sealed class GolfScreenEvent(){
    object OnTouch: GolfScreenEvent()
    object OnPause: GolfScreenEvent()
    object ToSettings: GolfScreenEvent()
    object OnContinue: GolfScreenEvent()
    object ToMenu: GolfScreenEvent()
}
