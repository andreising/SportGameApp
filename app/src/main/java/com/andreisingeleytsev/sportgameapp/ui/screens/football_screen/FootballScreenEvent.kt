package com.andreisingeleytsev.sportgameapp.ui.screens.football_screen

sealed class FootballScreenEvent(){
    data class OnRotateChange(val x: Float, val y: Float): FootballScreenEvent()
    object OnPushTheBall: FootballScreenEvent()
    object OnPause: FootballScreenEvent()
    object ToSettings: FootballScreenEvent()
    object OnContinue: FootballScreenEvent()
    object ToMenu: FootballScreenEvent()
}
