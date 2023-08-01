package com.andreisingeleytsev.sportgameapp.ui.screens.golf_screen

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.sportgameapp.ui.screens.football_screen.FootballScreenEvent
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class GolfScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: GolfScreenEvent){
        when(event) {
            is GolfScreenEvent.OnTouch -> {
                if (isGameOn) {
                    isBallGo = true
                    angleTimer.cancel()
                    ballTimer.start()
                }

            }
            is GolfScreenEvent.OnPause-> {
                if (!isSettingsOpen.value) openSettings()
                else closeSettings()
            }
            is GolfScreenEvent.ToSettings ->{
                sendUIEvent(UIEvents.OnNavigate(Routes.SETTINGS_SCREEN))
            }
            is GolfScreenEvent.OnContinue ->{
                closeSettings()
            }
            is GolfScreenEvent.ToMenu ->{
                sendUIEvent(UIEvents.OnNavigate(Routes.MAIN_SCREEN))
            }
        }
    }

    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    var height = 0.dp
    var width = 0.dp
    var context: Activity? = null

    val arrowOffsetX = mutableStateOf(0.dp)
    val arrowOffsetY = mutableStateOf(0.dp)
    val arrowRotate = mutableStateOf(0F)
    var arrowRadius= 0.dp
    var arrowAngle = 90.0
    var move = 1.0
    fun changeAngle(){
        if (arrowAngle+move !in 0.0..180.0) move*=-1
        arrowAngle+=move
        arrowRotate.value -= move.toFloat()*1F
        val radians = Math.toRadians(arrowAngle)
        arrowOffsetY.value = sin(radians) *arrowRadius
        arrowOffsetX.value = cos(radians) *arrowRadius
    }
    private val angleTimer = object : CountDownTimer(100000, 30){
        override fun onTick(p0: Long) {
            changeAngle()
        }

        override fun onFinish() {

        }
    }
    var ballSize = 0.dp
    var ballStartX = 0.dp
    var ballStartY = 0.dp
    val ballXOffset = mutableStateOf(0.dp)
    val ballYOffset = mutableStateOf(0.dp)
    private val ballTimer = object : CountDownTimer(10000, 10){
        val vectorSpeed = 3

        override fun onTick(p0: Long) {
            checkRicochet()
            val radians = Math.toRadians(arrowAngle)
            ballXOffset.value -= cos(radians) * vectorSpeed.dp
            ballYOffset.value += sin(radians) * vectorSpeed.dp

        }

        override fun onFinish() {

        }

    }

    private fun openSettings(){
        isGameOn = false
        isSettingsOpen.value = true
        angleTimer.cancel()
        ballTimer.cancel()
    }

    var isBallGo = false
    private fun closeSettings(){
        isSettingsOpen.value = false
        angleTimer.start()
        if (isBallGo) ballTimer.start()
        object : CountDownTimer(200,200){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                isGameOn=true
            }

        }.start()
    }



    private fun checkRicochet() {
        val newY = ballStartY - ballYOffset.value+ballSize/2
        val newX = ballStartX - ballXOffset.value+ballSize/2
        if (newX !in 0.dp..width || newY !in 0.dp..height) endGame()
        if (checkFirstHole(newX, newY)) ballInHole()
        if (checkSecondHole(newX, newY)) ballInHole()
        if (checkThirdHole(newX, newY)) ballInHole()
        if (checkFourthHole(newX, newY)) ballInHole()
    }

    private fun endGame() {
        ballTimer.cancel()
        context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            ?.edit()?.putInt("key", score.value)?.apply()
        sendUIEvent(UIEvents.OnNavigate(Routes.RESULT_SCREEN))
    }

    private fun ballInHole() {
        ballTimer.cancel()
        ballYOffset.value = 0.dp
        ballXOffset.value = 0.dp
        isBallGo = false
        score.value++
        angleTimer.start()
    }


    private fun checkFirstHole(newX: Dp, newY: Dp): Boolean {
        val fieldWidth = 360*height/800

        val hole1OffsetX = width/2-160*fieldWidth/360
        val hole1OffsetY = minOf(575*height/800, 575.dp)
        return newX in hole1OffsetX..hole1OffsetX+ballSize&&newY in hole1OffsetY..hole1OffsetY+ballSize
    }
    private fun checkSecondHole(newX: Dp, newY: Dp): Boolean {
        val fieldWidth = 360*height/800
        val hole1OffsetX = width/2-60*fieldWidth/360
        val hole1OffsetY = minOf(325*height/800, 325.dp)
        return newX in hole1OffsetX..hole1OffsetX+ballSize&&newY in hole1OffsetY..hole1OffsetY+ballSize
    }
    private fun checkThirdHole(newX: Dp, newY: Dp): Boolean {
        val fieldWidth = 360*height/800
        val hole1OffsetX = width/2+89*fieldWidth/360
        val hole1OffsetY = minOf(175*height/800, 175.dp)
        return newX in hole1OffsetX..hole1OffsetX+ballSize&&newY in hole1OffsetY..hole1OffsetY+ballSize
    }
    private fun checkFourthHole(newX: Dp, newY: Dp): Boolean {
        val fieldWidth = 360*height/800
        val hole1OffsetX = width/2+135*fieldWidth/360
        val hole1OffsetY = minOf(500*height/800, 500.dp)
        return newX in hole1OffsetX..hole1OffsetX+ballSize&&newY in hole1OffsetY..hole1OffsetY+ballSize
    }


    var isGameOn = true
    val score = mutableStateOf(0)
    val isSettingsOpen = mutableStateOf(false)
    init {
        angleTimer.start()
    }
}