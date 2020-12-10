package com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI

import android.os.Handler
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.instFiniteButtonsAfterResume
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.PrefUtil
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Andreas on 11/27/2020.
 */


fun MainActivity.timerAnimationUp() {

    timer_PNG.animate().apply{
        duration = 1000
        rotationY(-360F)
        translationY(-180f)
    }.start()

}

fun MainActivity.timerAnimationDown() {

    timer_PNG.animate().apply{
        duration = 1000
        rotationY(360F)
        translationY(9f)
    }.start()

}

fun MainActivity.timerRotation() {

    timer_PNG.animate().apply {
        duration = 2000
        rotationBy(360f)
    }.start()

}

fun MainActivity.timerShrink() {

    timer_PNG.animate().apply {
        duration = 1000
        scaleX(0.7f)
        scaleY(0.7f)
    }.start()

}

fun MainActivity.timerEnlarge() {

    timer_PNG.animate().apply {
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

    val timerState = PrefUtil.getTimerState(this)

    if(timerState == TimerState.Running && sharedPreferencesInitialization.timerOn) {

        //handles delay events for animations
        val handler = Handler()

        handler.postDelayed({ timerAnimationUp() }, 700)
        handler.postDelayed({ timerShrink() }, 1700)
        handler.postDelayed({ instFiniteButtonsAfterResume() },2000)
        handler.postDelayed({ timerRotation() }, 3000)
    }
}

fun MainActivity.pulsateAnimation(){

    val handler = Handler()
    handler.postDelayed({ timer_PNG.animate().apply {
        duration = 450
        scaleX(0.8f)
        scaleY(0.8f)
    }.start() }, 0)

    handler.postDelayed({ timer_PNG.animate().apply {
        duration = 450
        scaleX(0.7f)
        scaleY(0.7f)
    }.start() }, 455)

}