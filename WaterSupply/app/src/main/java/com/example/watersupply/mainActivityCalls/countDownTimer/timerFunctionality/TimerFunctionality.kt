package com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality


import android.os.CountDownTimer


/**
 * Created by Andreas on 11/24/2020.
 */
class TimerFunctionality {

    private val sharedPreferencesInitialization =
        com.example.watersupply.SharedPreferencesInitialization()

    var convertedTimerCountToHours: Long = (sharedPreferencesInitialization.timerSeekBarValueOutput * 3600).toLong()
    var detectTimeChange : Long = 0L

    var secondsRemaining = convertedTimerCountToHours
    var timerLengthSeconds = 0L

    var timerState = TimerState.Paused
    lateinit var countDownTimer: CountDownTimer
}