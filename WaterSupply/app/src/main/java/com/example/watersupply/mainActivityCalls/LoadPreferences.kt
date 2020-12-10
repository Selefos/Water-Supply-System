package com.example.watersupply.mainActivityCalls


import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.watersupply.R
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.resetTimer
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Andreas on 11/26/2020.
 */


     fun MainActivity.loadDarkModePrefs() {

        val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spDarkModePrefs, Context.MODE_PRIVATE)
        sharedPreferencesInitialization.darkModeValueOutput = sharedPreferences.getBoolean(sharedPreferencesInitialization.spDarkModeState, false)

        if(sharedPreferencesInitialization.darkModeValueOutput)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        if(!sharedPreferencesInitialization.darkModeValueOutput)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

     fun MainActivity.loadTimerPrefs() {

        val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerPrefs, Context.MODE_PRIVATE)
        sharedPreferencesInitialization.timerOn = sharedPreferences.getBoolean(sharedPreferencesInitialization.spTimerSwitchState, false)

        if(sharedPreferencesInitialization.timerOn)
            timer_PNG.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.timer_enabled))


        if(!sharedPreferencesInitialization.timerOn) {
           timer_PNG.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.timer_disabled))
           resetTimer()
        }

    }

     fun MainActivity.loadTimerSeekBarPrefs() {

        val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerSeekBarPrefs, Context.MODE_PRIVATE)
        sharedPreferencesInitialization.timerSeekBarValueOutput = sharedPreferences.getLong(sharedPreferencesInitialization.spTimerSeekBarState, 0) + 1
        timerFunctionality.convertedTimerCountToHours = sharedPreferencesInitialization.timerSeekBarValueOutput * 3600
        timerFunctionality.secondsRemaining = timerFunctionality.convertedTimerCountToHours

    }

