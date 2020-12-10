package com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality

import  android.content.Context
import android.preference.PreferenceManager


/**
 * Created by Andreas on 11/21/2020.
 */

class PrefUtil {


    var stPreviousTimeChoice : String = "previousTimeStoring"
    var stPreTime : String = "preTime"
    var storePreviousTime : Long = 0

    companion object{

        fun getTimerLength(context: Context): Long {
            //placeholder
            return 0
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.waterSupply.timer.previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }


        private const val TIMER_STATE_ID = "com.waterSupply.timer.timer_state"

        fun getTimerState(context: Context) : TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return TimerState.values()[ordinal]
        }

        fun setTimerState(state:TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }


        private const val SECONDS_REMAINING_ID = "com.waterSupply.timer.previous_timer_length"

        fun getSecondsRemaining(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }


        private const val ALARM_SET_TIME_ID = "com.waterSupply.timer.background_time"

        fun getAlarmSetTime(context: Context) : Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(ALARM_SET_TIME_ID,0)
        }

        fun setAlarmSetTime(time : Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }

    }
}