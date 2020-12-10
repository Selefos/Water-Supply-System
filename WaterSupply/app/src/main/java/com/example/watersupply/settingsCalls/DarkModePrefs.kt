package com.example.watersupply.settingsCalls

import android.app.UiModeManager
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by Andreas on 11/27/2020.
 */


 fun Settings.darkMode() {

    sharedPreferencesInitialization.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->

        saveDarkModePrefs()

        if (isChecked)
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        if (!isChecked)
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
}

 fun Settings.saveDarkModePrefs() {

    val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spDarkModePrefs, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    //save for Settings.kt
    editor.putBoolean(sharedPreferencesInitialization.spDarkModeSwitch, sharedPreferencesInitialization.darkModeSwitch.isChecked)

    sharedPreferencesInitialization.darkModeValueInput = sharedPreferencesInitialization.darkModeSwitch.isChecked

    //save for MainActivity.kt
    editor.putBoolean(sharedPreferencesInitialization.spDarkModeState, sharedPreferencesInitialization.darkModeValueInput)
    editor.apply()

}

 fun Settings.setDarkModePrefs() {

    val sharedPreferences = getSharedPreferences(sharedPreferencesInitialization.spDarkModePrefs, Context.MODE_PRIVATE)
    sharedPreferencesInitialization.darkModeOnOff = sharedPreferences.getBoolean(sharedPreferencesInitialization.spDarkModeSwitch, false)

}