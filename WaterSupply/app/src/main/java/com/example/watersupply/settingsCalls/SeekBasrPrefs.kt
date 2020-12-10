package com.example.watersupply.settingsCalls

import android.content.Context
import android.widget.SeekBar

/**
 * Created by Andreas on 11/27/2020.
 */


 fun Settings.timerSeekBarTimeSet() {

    sharedPreferencesInitialization.timerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {


            sharedPreferencesInitialization.timerSeekBarValueInput = progress.toLong()
            val textViewText = ((sharedPreferencesInitialization.timerSeekBarValueInput)+1L).toString() + "h"
            sharedPreferencesInitialization.timerEditTextPrefs.setText(textViewText)
            saveTimerSeekBarPrefs()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    })

}

 fun Settings.saveTimerSeekBarPrefs() {

    val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerSeekBarPrefs, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    //save for Settings.kt
    editor.putLong(sharedPreferencesInitialization.spTimerSeekBarPrefs, sharedPreferencesInitialization.timerSeekBarValueInput)

    //save for MainActivity.kt
    editor.putLong(sharedPreferencesInitialization.spTimerSeekBarState, sharedPreferencesInitialization.timerSeekBarValueInput)
    editor.apply()

}

 fun Settings.setTimerSeekBarPrefs() {

    val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerSeekBarPrefs, Context.MODE_PRIVATE)
    sharedPreferencesInitialization.timerSeekBarView = sharedPreferences.getLong(sharedPreferencesInitialization.spTimerSeekBarPrefs, -1)

}