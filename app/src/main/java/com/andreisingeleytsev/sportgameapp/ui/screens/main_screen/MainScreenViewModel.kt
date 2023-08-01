package com.andreisingeleytsev.sportgameapp.ui.screens.main_screen

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.sportgameapp.ui.screens.rules_screen.RulesScreenEvent
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: MainScreenEvent){
        when(event) {
            is MainScreenEvent.OnNavigateButtonPressed -> {
                if (event.mainScreenRoute!= SETTINGS) menuState.value = event.mainScreenRoute
                else sendUIEvent(UIEvents.OnNavigate(Routes.SETTINGS_SCREEN))
            }
            is MainScreenEvent.OnDifficultyChoose -> {
                context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                    ?.edit()?.putInt("difficulty", event.difficulty)?.apply()
                difficulty.value = event.difficulty
            }
            is MainScreenEvent.OnLevelChoose -> {
                if (event.level==1) sendUIEvent(UIEvents.OnNavigate(Routes.FOOTBALL_SCREEN))
                if (event.level==2) sendUIEvent(UIEvents.OnNavigate(Routes.HOCKEY_SCREEN))
                if (event.level==3) sendUIEvent(UIEvents.OnNavigate(Routes.GOLF_SCREEN))
            }
            else->{}
        }
    }
    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    var context: Activity? = null
    val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    val menuState = mutableStateOf(MAIN)
    val difficulty = mutableStateOf(
        sharedPreferences?.getInt("difficulty", 1) ?: 1
    )
}
const val MAIN = "main"
const val SETTINGS = "settings"
const val LEVELS = "levels"
const val DIFFICULTY = "difficulty"