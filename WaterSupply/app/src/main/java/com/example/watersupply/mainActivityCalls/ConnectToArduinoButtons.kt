package com.example.watersupply.mainActivityCalls

import android.graphics.Color
import android.os.SystemClock
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.watersupply.mainActivityCalls.chronometerTimer.*
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.onTimerStartAnimation
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.onTimerStopAnimation
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.startTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.loadPrefsEstimatedClockStopTime
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.savePrefsEstimatedClockStopTimeOnMainActivity
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState

/**
 * Created by Andreas on 11/27/2020.
 */

                    /*---- BUTTON TIMER ----*/

    //change the button views accordingly when timer -> ON
    fun MainActivity.buttonTimerOnFinite() {

        binding.buttonTimerOnFinite.isVisible = true

        binding.buttonTimerOnFinite.setOnClickListener{

            //sendSMSInitiateCountdown()

            connectionMessage()

            binding.clockStopTime.isVisible = true


            savePrefsEstimatedClockStopTimeOnMainActivity()
            loadPrefsEstimatedClockStopTime()

            //init values to cycle through button views when timer -> ON
            buttonTimerOnFiniteInit()

            arduinoInitiate()

            //starts the timer
            timerFunctionality.timerState = TimerState.Running
            startTimer()
            onTimerStartAnimation()
        }
    }

    fun MainActivity.buttonTimerOffFinite() {

        binding.buttonTimerOffFinite.isVisible = true

        binding.buttonTimerOffFinite.setOnClickListener{

            //sendSMSDeactivateCountdown()

            disconnectionMessage()

            //init values to cycle through button views when timer -> ON
            buttonTimerOffFiniteInit()

            arduinoStop()

            //pauses the timer
            onTimerStopAnimation()
            binding.clockView.isVisible = false
            binding.clockStopTime.isVisible = false
            timerFunctionality.countDownTimer.cancel()
            timerFunctionality.timerState = TimerState.Paused
        }
    }

    //init values to cycle through button views when timer -> ON
    fun MainActivity.instantiateTimerButtonsAfterResume(){

        if(timerFunctionality.timerState == TimerState.Running && sharedPreferencesInitialization.timerOn)
            buttonTimerOnFiniteInit()

        if(timerFunctionality.timerState == TimerState.Stopped && sharedPreferencesInitialization.timerOn)
            buttonTimerOffFiniteInit()
    }

    fun MainActivity.buttonTimerOnFiniteInit() {

        binding.buttonTimerOnFinite.isVisible = false
        buttonTimerOffFinite()
    }

    fun MainActivity.buttonTimerOffFiniteInit() {

        binding.buttonTimerOffFinite.isVisible = false
        buttonTimerOnFinite()
    }

    /*-------------------------------------------------------------*/

                    /*---- BUTTON CHRONOMETER ----*/

    //change the button views accordingly when timer -> OFF
    fun MainActivity.buttonChronometerOnInfinite(){

        binding.buttonTimerOnInfinite.isVisible = true

        binding.buttonTimerOnInfinite.setOnClickListener{

            //sendSMSInitiateNoTimer()

            connectionMessage()

            arduinoInitiate()

            getElapsedChronometerTime(SystemClock.elapsedRealtime())
            //init values to cycle through button views when timer -> OFF
            buttonChronometerOnInfiniteInit()

            startChronometer()
        }
    }

    fun MainActivity.buttonChronometerOffInfinite(){

        binding.buttonTimerOffInfinite.isVisible = true

        binding.buttonTimerOffInfinite.setOnClickListener {

            //sendSMSDeactivateNoTimer()

            disconnectionMessage()

            arduinoStop()

            //init values to cycle through button views when timer -> OFF
            buttonChronometerOffInfiniteInit()

            stopChronometer()
        }
    }

    //init values to cycle through button views when timer -> OFF
    fun MainActivity.instantiateChronometerButtonsAfterResume(){

        chronometerFunctionality.chronometerState = PrefUtilChronometer.getChronometerState(this)

        if(chronometerFunctionality.chronometerState == ChronometerState.Running && !sharedPreferencesInitialization.timerOn) {
            buttonChronometerOnInfiniteInit()
            continueChronometer()
        }

        if(chronometerFunctionality.chronometerState == ChronometerState.Stopped && !sharedPreferencesInitialization.timerOn)
            buttonChronometerOffInfiniteInit()
    }

    fun MainActivity.buttonChronometerOnInfiniteInit(){

        binding.buttonTimerOnInfinite.isVisible = false
        buttonChronometerOffInfinite()
    }

    fun MainActivity.buttonChronometerOffInfiniteInit(){

        binding.buttonTimerOffInfinite.isVisible = false
        buttonChronometerOnInfinite()
    }

    /*-------------------------------------------------------------*/

    //depending if timer selection ON or OFF instantiate the button pairs
    fun MainActivity.instButtonBaseTimerChronometerChoice(){

        if(sharedPreferencesInitialization.timerOn)
            buttonTimerOnFinite()

        if(!sharedPreferencesInitialization.timerOn)
            buttonChronometerOnInfinite()
    }


    fun MainActivity.connectionMessage(){
        val text = "Connection to Arduino\nInitiated"
        val spannableString = SpannableString(text)
        val txtColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(txtColor, 22,text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(applicationContext, spannableString, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun MainActivity.disconnectionMessage(){
        val text = "Connection to Arduino\nDisconnected"
        val spannableString = SpannableString(text)
        val txtColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(txtColor, 22,text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(applicationContext, spannableString, Toast.LENGTH_SHORT)
        toast.show()
    }