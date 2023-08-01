package com.andreisingeleytsev.sportgameapp.ui.screens.splash_screen

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(): ViewModel() {
    var isFirstLaunch = mutableStateOf<Boolean?>(null)
    var _isFirstLaunch = true
    init {
        viewModelScope.launch {
            delay(1000)
            _isFirstLaunch = isFirstLaunch()
            delay(3000)
            isFirstLaunch.value = _isFirstLaunch
        }
    }
    var context: Application? = null
    private fun isFirstLaunch(): Boolean {
        val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val myData = sharedPreferences?.getInt("key", -1)
        return myData==-1
    }
}