package com.example.watersupply.mainActivityCalls

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.watersupply.R
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.resetTimer

/**
 * Created by Andreas on 11/26/2020.
 */

    fun MainActivity.loadDarkModePrefs() {

            val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spDarkModePrefs, Context.MODE_PRIVATE)
            sharedPreferencesInitialization.darkModeValueOutput = sharedPreferences.getBoolean(sharedPreferencesInitialization.spDarkModeState, false)

            if(sharedPreferencesInitialization.darkModeValueOutput)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun MainActivity.loadTimerSeekBarPrefs() {

            val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerSeekBarPrefs, Context.MODE_PRIVATE)
            sharedPreferencesInitialization.timerSeekBarValueOutput = sharedPreferences.getInt(sharedPreferencesInitialization.spTimerSeekBarState, 0) + 1
            timerFunctionality.convertedTimerCountToHours = (sharedPreferencesInitialization.timerSeekBarValueOutput * 3600).toLong()
            timerFunctionality.secondsRemaining = timerFunctionality.convertedTimerCountToHours
    }

    fun MainActivity.loadTimerPrefs() {

            val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerPrefs, Context.MODE_PRIVATE)
            sharedPreferencesInitialization.timerOn = sharedPreferences.getBoolean(sharedPreferencesInitialization.spTimerSwitchState, false)

            if(sharedPreferencesInitialization.timerOn)
                binding.timerPNG.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.timer_enabled))


            if(!sharedPreferencesInitialization.timerOn) {
                binding.timerPNG.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.timer_disabled))
                timerFunctionality.convertedTimerCountToHours = 0
                timerFunctionality.secondsRemaining = 0
                resetTimer()
            }
    }