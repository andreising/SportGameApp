package com.andreisingeleytsev.sportgameapp.ui.screens.main_screen

sealed class MainScreenEvent{
    data class OnLevelChoose(val level: Int): MainScreenEvent()
    data class OnNavigateButtonPressed(val mainScreenRoute: String): MainScreenEvent()
    data class OnDifficultyChoose(val difficulty: Int): MainScreenEvent()
    data class OnValueChanged(val difficulty: Int): MainScreenEvent()
    object OnSoundOn: MainScreenEvent()
    object OnSoundOff: MainScreenEvent()
}
