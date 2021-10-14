package com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI

import android.os.Handler
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.instantiateTimerButtonsAfterResume
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.PrefUtilTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState

/**
 * Created by Andreas on 11/27/2020.
 */

    fun MainActivity.timerAnimationUp() {

        binding.timerPNG.animate().apply{
            duration = 1000
            rotationY(-360F)
            translationY(-180f)
        }.start()
    }

    fun MainActivity.timerAnimationDown() {

        binding.timerPNG.animate().apply{
            duration = 1000
            rotationY(360F)
            translationY(9f)
        }.start()
    }

    fun MainActivity.timerRotation() {

        binding.timerPNG.animate().apply {
            duration = 2000
            rotationBy(360f)
        }.start()
    }

    fun MainActivity.timerShrink() {

        binding.timerPNG.animate().apply {
            duration = 1000
            scaleX(0.7f)
            scaleY(0.7f)
        }.start()
    }

    fun MainActivity.timerEnlarge() {

        binding.timerPNG.animate().apply {
            duration = 2000
            scaleX(1f)
            scaleY(1f)
        }.start()
    }

    fun MainActivity.onTimerStartAnimation() {

        timerAnimationUp()

        //handles delay events for animations
        val handler = Handler()
        //handler.postDelayed({ timerShrink() }, 1000)
        handler.postDelayed({ timerRotation() }, 2000)
    }

    fun MainActivity.onTimerStopAnimation(){

        timerAnimationDown()

        //handles delay events for animations
        val handler = Handler()
        handler.postDelayed({ timerEnlarge() }, 1000)
    }

    fun MainActivity.activateTimerAnimation(){

        val timerState = PrefUtilTimer.getTimerState(this)

        if(timerState == TimerState.Running && sharedPreferencesInitialization.timerOn) {

            //handles delay events for animations
            val handler = Handler()

            handler.postDelayed({ timerAnimationUp() }, 700)
            handler.postDelayed({ timerShrink() }, 1700)
            handler.postDelayed({ instantiateTimerButtonsAfterResume() },2000)
            handler.postDelayed({ timerRotation() }, 3000)
        }
    }

    fun MainActivity.pulsateAnimation(){

        val handler = Handler()
        handler.postDelayed({  binding.timerPNG.animate().apply {
            duration = 450
            scaleX(0.8f)
            scaleY(0.8f)
        }.start() }, 0)

        handler.postDelayed({  binding.timerPNG.animate().apply {
            duration = 450
            scaleX(0.7f)
            scaleY(0.7f)
        }.start() }, 500)
    }