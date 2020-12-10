package com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality


import android.os.CountDownTimer
import com.example.watersupply.SharedPreferencesInitialization


/**
 * Created by Andreas on 11/24/2020.
 */
class TimerFunctionality {


    private val sharedPreferencesInitialization = SharedPreferencesInitialization()


    var convertedTimerCountToHours: Long = sharedPreferencesInitialization.timerSeekBarValueOutput * 3600
    var detectTimeChange : Long = 0L

    var secondsRemaining = convertedTimerCountToHours
    var timerLengthSeconds = 0L

    var timerState = TimerState.Paused
    lateinit var countDownTimer: CountDownTimer


}