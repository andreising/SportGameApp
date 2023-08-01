package com.andreisingeleytsev.sportgameapp.ui.screens.hockey_screen

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class HockeyScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: HockeyScreenEvent){
        when(event) {
            is HockeyScreenEvent.OnTouch -> {
                onWashMove()
            }
            is HockeyScreenEvent.OnPause -> {
                if (!isSettingsOpen.value) openSettings()
                else closeSettings()
            }
            is HockeyScreenEvent.OnContinue->{
                closeSettings()
            }
            is HockeyScreenEvent.ToMenu->{
                sendUIEvent(UIEvents.OnNavigate(Routes.MAIN_SCREEN))
            }
            is HockeyScreenEvent.ToSettings->{
                sendUIEvent(UIEvents.OnNavigate(Routes.SETTINGS_SCREEN))
            }
        }
    }
    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    val isSettingsOpen = mutableStateOf(false)
    val score = mutableStateOf(0)

    fun openSettings(){
        isSettingsOpen.value = true
        timer.cancel()
        washerTimer.cancel()
    }
    fun closeSettings(){
        isSettingsOpen.value = false
        timer.start()
        washerTimer.start()
    }


    var platformAmpl = 90
    var platformSize = 0.dp
    val platformOffsetX = mutableStateOf(0)
    val platformOffsetY = mutableStateOf(0.dp)
    var platformXStart = 0.dp
    var topPadding = 0.dp


    private val timer: CountDownTimer = object : CountDownTimer(100000, 30){
        var module = 5
        override fun onTick(p0: Long) {
            if (platformOffsetX.value+module !in 0 ..platformAmpl*2) module *= -1

            platformOffsetX.value += module
        }

        override fun onFinish() {
            onMovePlatform()
        }

    }
    private fun onMovePlatform() {
        timer.start()
    }
    var washerSize = 0.dp
    var defaultWasherHeight = 0
    var washerXPosition = 0.dp
    var move = 10
    val washerOffset = mutableStateOf(0)

    private val washerTimer = object : CountDownTimer(1000000, 30){

        override fun onTick(p0: Long) {
            Log.d("tag", "check")
            checkRicochet()
            washerOffset.value+=move
        }

        override fun onFinish() {

        }

    }
    private fun onWashMove() {

        washerTimer.start()
    }

    private fun checkRicochet() {
        val newY = washerOffset.value + move
        if (newY.dp in defaultWasherHeight.dp-topPadding..defaultWasherHeight.dp) washOnCenter()
        if (defaultWasherHeight.dp - newY.dp in platformOffsetY.value..platformOffsetY.value+10.dp
            &&
            washerXPosition + washerSize / 2 in platformXStart..platformXStart+platformOffsetX.value.dp + platformSize) onWasherBack()
    }

    private fun washOnCenter(){
        washerTimer.cancel()
        score.value+=1
        washerOffset.value = 0

    }
    private fun onWasherBack(){
        move *=-1
        object:CountDownTimer(1200, 2000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                move=0
                stopGame()
            }

        }.start()
    }
    var context: Activity? = null
    private fun stopGame() {
        context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            ?.edit()?.putInt("key", score.value)?.apply()
        sendUIEvent(UIEvents.OnNavigate(Routes.RESULT_SCREEN))
    }

    init {
        onMovePlatform()

    }


}