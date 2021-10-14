package com.example.watersupply.settingsCalls

import android.content.Context
import android.widget.SeekBar
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.savePrefsEstimatedClockStopTimeOnSettingsActivity


/**
 * Created by Andreas on 11/27/2020.
 */

    fun Settings.timerSeekBarTimeSet() {

        sharedPreferencesInitialization.timerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                sharedPreferencesInitialization.timerSeekBarValueInput = progress
                val textViewText = ((sharedPreferencesInitialization.timerSeekBarValueInput)+1).toString() + "h"
                sharedPreferencesInitialization.timerEditTextPrefs.setText(textViewText)
                saveTimerSeekBarPrefs()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
               savePrefsEstimatedClockStopTimeOnSettingsActivity()
            }
        })
    }

    fun Settings.saveTimerSeekBarPrefs() {

        val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerSeekBarPrefs, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //save for Settings.kt
        editor.putInt(sharedPreferencesInitialization.spTimerSeekBarPrefs, sharedPreferencesInitialization.timerSeekBarValueInput)

        //save for MainActivity.kt
        editor.putInt(sharedPreferencesInitialization.spTimerSeekBarState, sharedPreferencesInitialization.timerSeekBarValueInput)
        editor.apply()
    }

    fun Settings.loadTimerSeekBarPrefs() {

        val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerSeekBarPrefs, Context.MODE_PRIVATE)
        sharedPreferencesInitialization.timerSeekBarView = sharedPreferences.getInt(sharedPreferencesInitialization.spTimerSeekBarPrefs, 0).toLong()
    }