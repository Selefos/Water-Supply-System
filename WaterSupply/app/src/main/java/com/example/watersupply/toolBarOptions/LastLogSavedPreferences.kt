package com.example.watersupply.toolBarOptions

import android.content.Context
import com.example.watersupply.mainActivityCalls.MainActivity
import java.text.DateFormat
import java.time.LocalTime
import java.util.*

/**
 * Created by Andreas on 12/16/2020.
 */

    fun MainActivity.saveLastLogDetails(){

        val simpleDateFormat = DateFormat.getDateInstance()
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        val getTimeOfLastLogActivity = LocalTime.now().toString()
        val activityTimer = (timerFunctionality.convertedTimerCountToHours)/3600L

        val sharedPreferences = getSharedPreferences(lastLogPrefsInit.lgLastLogPrefs, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(lastLogPrefsInit.lgLastLogDate, currentDateAndTime)
        editor.putString(lastLogPrefsInit.lgLastLogTime, getTimeOfLastLogActivity)
        editor.putLong(lastLogPrefsInit.lgLastLogActivityTimer, activityTimer)
        editor.apply()
    }

    fun MainActivity.loadLastLogDetails(){
        val sharedPreferences = getSharedPreferences(lastLogPrefsInit.lgLastLogPrefs, Context.MODE_PRIVATE)
        lastLogPrefsInit.lastLogDate = sharedPreferences.getString(lastLogPrefsInit.lgLastLogDate, "")
        lastLogPrefsInit.lastLogTime = sharedPreferences.getString(lastLogPrefsInit.lgLastLogTime,"")
        lastLogPrefsInit.lastLogActivityTimer = sharedPreferences.getLong(lastLogPrefsInit.lgLastLogActivityTimer, -1)
    }