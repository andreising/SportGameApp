package com.andreisingeleytsev.sportgameapp.ui.screens.football_screen

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.sportgameapp.ui.utils.Routes
import com.andreisingeleytsev.sportgameapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class FootballScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: FootballScreenEvent){
        when(event) {
            is FootballScreenEvent.OnRotateChange -> {
                changeAngle(event.x, event.y)
            }
            is FootballScreenEvent.OnPushTheBall -> {
                pushBall()
            }
            is FootballScreenEvent.OnPause-> {
                if (!isSettingsOpen.value) openSettings()
                else closeSettings()
            }
            is FootballScreenEvent.ToSettings ->{
                sendUIEvent(UIEvents.OnNavigate(Routes.SETTINGS_SCREEN))
            }
            is FootballScreenEvent.OnContinue ->{
                closeSettings()
            }
            is FootballScreenEvent.ToMenu ->{
                sendUIEvent(UIEvents.OnNavigate(Routes.MAIN_SCREEN))
            }

            else -> {}
        }
    }
    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    var width = 180.dp
    var height = 0.dp
    var fieldWidth = 0.dp
    var platformAmpl = 70.dp
    var context: Activity? = null
    val platformOffsetX = mutableStateOf(0)
    val platformOffsetY = mutableStateOf(0.dp)
    var platformXStart = 0.dp
    var topPadding = 0.dp

    var platformSize = 0.dp

    private val timer: CountDownTimer = object : CountDownTimer(100000, 30){
        var module = 5
        override fun onTick(p0: Long) {
            if ((platformOffsetX.value+module).dp !in -platformAmpl ..platformAmpl) module *= -1

            platformOffsetX.value += module
        }

        override fun onFinish() {
            onMovePlatform()
        }

    }
    private fun onMovePlatform() {
        viewModelScope.launch {
            delay(1000)
            platformOffsetY.value = minOf(85*height/838, 85.dp)
            timer.start()
        }

    }

    var arrowRadius = 0.dp
    var arrowRotate = mutableStateOf(0F)
        var centerWidthFloatX = 0F

        var centerWidthFloatY = 0F

    private fun changeAngle(x: Float, y: Float) {
        val angle = atan2(centerWidthFloatX-x, y-centerWidthFloatY )
        val floatAngle = angle*(180F / PI).toFloat()
        arrowRotate.value = floatAngle-180F
    }

    val ballSize = mutableStateOf(0.dp)
    var ballOffsetY = mutableStateOf(0.dp)
    val ballOffsetX = mutableStateOf(0.dp)
    var vectorSpeed = 2
    private val ballTimer = object : CountDownTimer(10000, 1){
        override fun onTick(p0: Long) {
            val radians = Math.toRadians(arrowRotate.value.toDouble())
            checkRicochet(sin(radians) * vectorSpeed.dp,cos(radians) * vectorSpeed.dp)
            ballOffsetX.value += sin(radians) * vectorSpeed.dp
            ballOffsetY.value -= cos(radians) * vectorSpeed.dp

        }

        override fun onFinish() {

        }

    }
    var count = 0
    private fun checkRicochet(x: Dp, y: Dp) {
        val newX = ballOffsetX.value + vectorSpeed*x
        val newY = minOf(665*height/838, 665.dp)+ballOffsetY.value - vectorSpeed*y

        if (checkGoal(newX, newY)) {
            score.value+=1
            ballOffsetX.value = 0.dp
            ballOffsetY.value = 0.dp
            ballTimer.cancel()
            return
        }
        if (checkOutField(newX, newY)) {
            ballTimer.cancel()
            if (count == 1) isGameOn()
            else {
                ballOffsetX.value = 0.dp
                ballOffsetY.value = 0.dp
                ballTimer.cancel()
                count++
            }
        }

        checkPlatformContact(newX, newY)

    }

    private fun isGameOn() {
        context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            ?.edit()?.putInt("key", score.value)?.apply()
        sendUIEvent(UIEvents.OnNavigate(Routes.RESULT_SCREEN))
    }

    private fun checkPlatformContact(newX: Dp, newY: Dp) {
        if (vectorSpeed==-1) return
        if (newY  in platformOffsetY.value..platformOffsetY.value + 6.dp
            &&
            newX in platformOffsetX.value.dp - platformSize/2..platformOffsetX.value.dp + platformSize/2
        ) vectorSpeed*=-1
    }

    private fun checkOutField(newX: Dp, newY: Dp): Boolean {
        return newX !in -width/2..width/2 || newY !in 0.dp..height
    }
    private fun checkGoal(newX: Dp, newY: Dp): Boolean{
        if (newY>0.dp) return false
        if (newX!in -platformAmpl..platformAmpl) return false
        return true
    }

    private fun pushBall(){
        ballTimer.start()
    }

    val score = mutableStateOf(0)
    val isSettingsOpen = mutableStateOf(false)


    private fun openSettings(){
        isSettingsOpen.value = true
        timer.cancel()
        ballTimer.cancel()
    }
    private fun closeSettings(){
        isSettingsOpen.value = false
        timer.start()
        ballTimer.start()
    }
    init {
        onMovePlatform()
    }
}