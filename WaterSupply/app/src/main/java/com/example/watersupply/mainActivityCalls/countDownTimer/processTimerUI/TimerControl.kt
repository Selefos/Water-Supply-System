package com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI

import android.content.Context
import android.os.CountDownTimer
import androidx.core.view.isVisible
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground
import com.example.watersupply.mainActivityCalls.buttonTimerOnFinite
import com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager.sendNotification
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.PrefUtilTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState


/**
 * Created by Andreas on 11/27/2020.
 */

    fun MainActivity.initTimer() {
        timerFunctionality.timerState = PrefUtilTimer.getTimerState(this)

        if(timerFunctionality.timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()


        timerFunctionality.secondsRemaining = if (timerFunctionality.timerState == TimerState.Running || timerFunctionality.timerState == TimerState.Paused)
            PrefUtilTimer.getSecondsRemaining(this)
        else
            timerFunctionality.timerLengthSeconds


        val alarmSetTime = PrefUtilTimer.getAlarmSetTime(this)
        //if 0 then the timer is not set
        //subtract the seconds that passed, while the app was closed, from last seconds that were saved in preferences
        if(alarmSetTime > 0)                        //get time in seconds upon background run
            timerFunctionality.secondsRemaining -= TimerRunOnBackground.currentSeconds - alarmSetTime

        if(timerFunctionality.secondsRemaining <= 0)
            onTimerFinished()

        //Resume timer after reopening the app
        else if(timerFunctionality.timerState == TimerState.Running)
            startTimer()

        updateCountdownUI()
    }

    fun MainActivity.startTimer() {

        binding.clockView.isVisible = true

        timerFunctionality.timerState = TimerState.Running
        timerFunctionality.countDownTimer = object : CountDownTimer(timerFunctionality.secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                timerFunctionality.secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }


    fun MainActivity.onTimerFinished() {

        timerFunctionality.timerState = TimerState.Stopped

        binding.clockView.text = null
        onTimerStopAnimation()
        buttonTimerOnFinite()

        //In case we want the timer to auto-reset after finished
        resetTimer()
    }

    fun MainActivity.resetTimer(){

        setNewTimerLength()
        PrefUtilTimer.setSecondsRemaining(timerFunctionality.timerLengthSeconds, this)
        timerFunctionality.secondsRemaining = timerFunctionality.timerLengthSeconds

        binding.clockView.isVisible = false

        updateCountdownUI()
    }


    fun MainActivity.setNewTimerLength() {

        //val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerFunctionality.timerLengthSeconds = timerFunctionality.convertedTimerCountToHours
    }

    fun MainActivity.setPreviousTimerLength() {

        timerFunctionality.timerLengthSeconds = PrefUtilTimer.getPreviousTimerLengthSeconds(this)
    }

    fun MainActivity.storePreviousTime(){

        //Store timer values to SharedPreferences
        val storePreviousTimeChoice = getSharedPreferences(prefUtil.stPreviousTimeChoice, Context.MODE_PRIVATE)
        val editor = storePreviousTimeChoice.edit()

        //Initialise time detector for case of time renewal
        //if the timer value is changed the timer auto restarts
        //else the timer continues

        timerFunctionality.detectTimeChange = storePreviousTimeChoice.getLong(prefUtil.stPreTime, -1)
        editor.putLong(prefUtil.stPreTime, timerFunctionality.convertedTimerCountToHours)
        editor.apply()

        if(timerFunctionality.convertedTimerCountToHours != timerFunctionality.detectTimeChange) {

            timerFunctionality.detectTimeChange = storePreviousTimeChoice.getLong(prefUtil.stPreTime, -1)
            //prefUtil.storePreviousTime = storePreviousTimeChoice.getLong(prefUtil.stPreTime, -1)

            resetTimer()
        }
    }


    fun MainActivity.updateCountdownUI() {

        val hoursUntilFinished = timerFunctionality.secondsRemaining / 3600
        val minutesUntilFinished = timerFunctionality.secondsRemaining / 60
        val minutes = minutesUntilFinished - hoursUntilFinished *60
        val secondsInMinutesUntilFinished = timerFunctionality.secondsRemaining - minutesUntilFinished * 60

        val timeLeftFormatted: String = String.format("%02d:%02d:%02d", hoursUntilFinished, minutes, secondsInMinutesUntilFinished)
        binding.clockView.text = timeLeftFormatted

        if(timerFunctionality.timerState == TimerState.Running)
        pulsateAnimation()

        //sends notification while app is open
        if(timerFunctionality.secondsRemaining == 1L)
            sendNotification()
    }