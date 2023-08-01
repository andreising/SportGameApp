package com.andreisingeleytsev.sportgameapp.ui.screens.rules_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen.HockeyScreenEvent
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RulesScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: RulesScreenEvent){
        when(event) {
            is RulesScreenEvent.OnBackButtonPressed -> {
                val currentPosition = position.value-1
                if (currentPosition!=0) position.value = currentPosition

            }
            is RulesScreenEvent.OnNextButtonPressed -> {
                val currentPosition = position.value+1
                if (currentPosition!=4) position.value = currentPosition
                else sendUIEvent(UIEvents.OnNavigate(""))
            }
        }
    }
    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    val position = mutableStateOf(1)
}