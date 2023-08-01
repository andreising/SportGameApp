package com.andreisingeleytsev.sportgameapp.ui.screens.rules_screen

sealed class RulesScreenEvent{
    object OnBackButtonPressed: RulesScreenEvent()
    object OnNextButtonPressed: RulesScreenEvent()

}
