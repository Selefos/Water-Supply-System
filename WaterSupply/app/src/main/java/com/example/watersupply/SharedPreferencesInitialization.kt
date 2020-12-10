package com.example.watersupply

import android.annotation.SuppressLint
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch
import kotlin.properties.Delegates

/**
 * Created by Andreas on 11/17/2020.
 *
 * This file contains:
 * Widget initializations for Settings.kt
 * Variable initializations for Shared Preferences
 * String TAGS for Shared Preferences
 */

@SuppressLint("UseSwitchCompatOrMaterialCode")
class SharedPreferencesInitialization {

  //region DARK_MODE PREFERENCES
 var spDarkModePrefs : String = "dark_mode_prefs"
 var spDarkModeSwitch : String = "dark_mode"
 lateinit var darkModeSwitch : Switch
 var darkModeValueInput : Boolean = false
 var darkModeOnOff : Boolean = false

 var spDarkModeState : String = "dark_mode_state"
 var darkModeValueOutput : Boolean = false
 //endregion

  //region TIMER PREFERENCES
 var spTimerPrefs : String = "timer_prefs"
 var spTimerSwitchState : String = "timer_state"
 var spTimerSwitch : String = "_timer"
 lateinit var timerSwitch : Switch
 var timerValueInput : Boolean = false
 var timerOn : Boolean = false

 //endregion

  //region TIMER EDIT_TEXT PREFERENCES
 var spTimerEditText : String = "timer_edit_text"
 lateinit var timerEditTextPrefs : EditText
 var timerEditTextValueInput : Int = 0

 var spTimerTimeState : String = "timer_edit_text_state"
 var timerEditTextValueOutput : Int = 0
 //endregion

  //region TIMER SEEK_BAR PREFERENCES
 var spTimerSeekBarPrefs : String = "timer_seekBar"
 lateinit var timerSeekBar : SeekBar
 var timerSeekBarValueInput : Long = 0
 var timerSeekBarView : Long = 0

 var spTimerSeekBarState : String = "timer_seek_bar_state"
 var timerSeekBarValueOutput : Long = 0

 //endregion

}