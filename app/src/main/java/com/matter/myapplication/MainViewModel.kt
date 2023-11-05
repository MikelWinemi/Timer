package com.matter.myapplication

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel(){
    private val _currentTime:MutableStateFlow<Long> = MutableStateFlow(totalTime)
    val currentTime: StateFlow<Long>
        get() = _currentTime

    private val _isTimerRunning: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean>
        get() = _isTimerRunning

    private val timer:CountDownTimer =
        object : CountDownTimer(totalTime, interval){
            override fun onTick(millsUntilFinished: Long) {
                _currentTime.value = millsUntilFinished
            }

            override fun onFinish() {
                _currentTime.value = totalTime
                _isTimerRunning.value = false
            }
        }


    companion object {
        const val totalTime = 30 * 1000L
        const val interval = 1000L
    }


    fun startTimer(){
        if (isTimerRunning.value){
            resetTimer()
        }
        timer.start()
        _isTimerRunning.value = true

    }

    fun restartTimer(){
        if (_isTimerRunning.value){
            resetTimer()
        }

    }

    private fun resetTimer(){
        timer.cancel()
        _currentTime.value = totalTime
        _isTimerRunning.value = false
    }


    override fun onCleared() {
        super.onCleared()
        restartTimer()
    }




}
