package com.example.watersupply.mainActivityCalls.chronometerTimer

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Created by Andreas on 12/29/2020.
 */
class PrefUtilChronometer {

    companion object{


        private const val CHRONOMETER_STATE_ID = "com.example.watersupply.mainActivityCalls.chronometerTimer"

        fun getChronometerState(context: Context) : ChronometerState {

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(CHRONOMETER_STATE_ID, 0)
            return ChronometerState.values()[ordinal]
        }

        fun setChronometerState(state: ChronometerState, context: Context){

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(CHRONOMETER_STATE_ID, ordinal)
            editor.apply()
        }
    }
}