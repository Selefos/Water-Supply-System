package com.example.watersupply.mainActivityCalls

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.watersupply.mainActivityCalls.chronometerTimer.startChronometer
import com.example.watersupply.mainActivityCalls.chronometerTimer.stopChronometer
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.onTimerStartAnimation
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.onTimerStopAnimation
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.startTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Andreas on 11/27/2020.
 */

            /*---- BUTTON FINITE ----*/

//change the button views accordingly when timer -> ON
fun MainActivity.buttonTimerOnFinite() {

    button_timerOn_finite.isVisible = true

    button_timerOn_finite.setOnClickListener{

        val text = "Connection to Arduino\nInitiated"
        val spannableString = SpannableString(text)
        val txtColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(txtColor, 22,text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(applicationContext, spannableString, Toast.LENGTH_SHORT)
        toast.show()


        //init values to cycle through button views when timer -> ON
        buttonOnFiniteInit()

        timerFunctionality.timerState = TimerState.Running
        startTimer()


        onTimerStartAnimation()


    }
}

fun MainActivity.buttonTimerOffFinite() {

    button_timerOff_finite.isVisible = true

    button_timerOff_finite.setOnClickListener{

        val text = "Connection to Arduino\nDisconnected"
        val spannableString = SpannableString(text)
        val txtColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(txtColor, 22,text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(applicationContext, spannableString, Toast.LENGTH_SHORT)
        toast.show()



        buttonOffFiniteInit()

        onTimerStopAnimation()
        clock_view.isVisible = false
        timerFunctionality.countDownTimer.cancel()
        timerFunctionality.timerState = TimerState.Paused

    }

}

//init values to cycle through button views when timer -> ON
fun MainActivity.instFiniteButtonsAfterResume(){

    if(timerFunctionality.timerState == TimerState.Running && sharedPreferencesInitialization.timerOn)
        buttonOnFiniteInit()


    if(timerFunctionality.timerState == TimerState.Stopped && sharedPreferencesInitialization.timerOn)
        buttonOffFiniteInit()

}

fun MainActivity.buttonOnFiniteInit() {

    button_timerOn_finite.isVisible = false
    buttonTimerOffFinite()

}

fun MainActivity.buttonOffFiniteInit() {

    button_timerOff_finite.isVisible = false
    buttonTimerOnFinite()

}

/*-------------------------------------------------------------*/

                /*---- BUTTON INFINITE ----*/

//change the button views accordingly when timer -> OFF
fun MainActivity.buttonTimerOnInfinite(){

    button_timerOn_infinite.isVisible = true

    button_timerOn_infinite.setOnClickListener{

        val text = "Connection to Arduino\nInitiated"
        val spannableString = SpannableString(text)
        val txtColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(txtColor, 22,text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(applicationContext, spannableString, Toast.LENGTH_SHORT)
        toast.show()

        startChronometer()
        buttonOnInfiniteInit()

    }

}

fun MainActivity.buttonTimerOffInfinite(){

    button_timerOff_infinite.isVisible =  true

    button_timerOff_infinite.setOnClickListener {

        val text = "Connection to Arduino\nDisconnected"
        val spannableString = SpannableString(text)
        val txtColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(txtColor, 22,text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val toast = Toast.makeText(applicationContext, spannableString, Toast.LENGTH_SHORT)
        toast.show()

        stopChronometer()
        buttonOffInfiniteInit()

    }
}

//init values to cycle through button views when timer -> OFF
fun MainActivity.instInfiniteButtonsAfterResume(){
    if(timerFunctionality.timerState == TimerState.Running && !sharedPreferencesInitialization.timerOn)
        buttonOnInfiniteInit()


    if(timerFunctionality.timerState == TimerState.Stopped && !sharedPreferencesInitialization.timerOn)
        buttonOffInfiniteInit()

}

fun MainActivity.buttonOnInfiniteInit(){

    button_timerOn_infinite.isVisible = false
    buttonTimerOffInfinite()

}

fun MainActivity.buttonOffInfiniteInit(){


    button_timerOff_infinite.isVisible = false
    buttonTimerOnInfinite()

}

/*-------------------------------------------------------------*/

//depending if timer selection ON or OFF instantiate the button pairs
fun MainActivity.instButtonBaseTimerChoice(){

    if(sharedPreferencesInitialization.timerOn)
        buttonTimerOnFinite()


    if(!sharedPreferencesInitialization.timerOn)
        buttonTimerOnInfinite()

}




