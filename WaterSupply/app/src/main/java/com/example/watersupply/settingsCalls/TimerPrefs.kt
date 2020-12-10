package com.example.watersupply.settingsCalls

import android.content.Context

/**
 * Created by Andreas on 11/27/2020.
 */


 fun Settings.timerOnOff() {

    sharedPreferencesInitialization.timerSwitch.setOnCheckedChangeListener { _, isChecked ->
        saveTimerPrefs()

        if (isChecked) {
            val disable = "Disable Timer"
            sharedPreferencesInitialization.timerSwitch.text = disable
            sharedPreferencesInitialization.timerEditTextPrefs.isEnabled = true
            sharedPreferencesInitialization.timerSeekBar.isEnabled = true
        }

        if (!isChecked) {
            val enable = "Enable Timer"
            sharedPreferencesInitialization.timerSwitch.text = enable
            sharedPreferencesInitialization.timerEditTextPrefs.isEnabled = false
            sharedPreferencesInitialization.timerSeekBar.isEnabled = false
        }
    }
}

 fun Settings.saveTimerPrefs() {

    val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerPrefs, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    //save for Settings.kt
    editor.putBoolean(sharedPreferencesInitialization.spTimerSwitch, sharedPreferencesInitialization.timerSwitch.isChecked)

    sharedPreferencesInitialization.timerValueInput = sharedPreferencesInitialization.timerSwitch.isChecked

    //save for MainActivity.kt
    editor.putBoolean(sharedPreferencesInitialization.spTimerSwitchState, sharedPreferencesInitialization.timerValueInput)
    editor.apply()

}

 fun Settings.setTimerPrefs() {

    val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spTimerPrefs, Context.MODE_PRIVATE)
    sharedPreferencesInitialization.timerOn = sharedPreferences.getBoolean(sharedPreferencesInitialization.spTimerSwitch, false)

}