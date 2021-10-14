package com.example.watersupply.mainActivityCalls.chronometerTimer

import android.content.Context.MODE_PRIVATE
import android.os.SystemClock
import android.widget.Chronometer
import androidx.core.view.isVisible
import com.example.watersupply.mainActivityCalls.MainActivity

/**
 * Created by Andreas on 12/5/2020.
 */

    private const val cmChronometerPrefs = "chronometerKey"
    private const val cmChronometerValue = "chronometerValue"

    fun MainActivity.chronometerInit() {

        binding.chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {

            val time = SystemClock.elapsedRealtime() - binding.chronometer.base
            val h = (time / 3600000)
            val m = (time - h * 3600000) / 60000
            val s = (time - h * 3600000 - m * 60000) / 1000
            val format = String.format("%02d:%02d:%02d", h, m, s)
            binding.chronometer.format = format
        }
    }

    fun MainActivity.getElapsedChronometerTime(getMillis : Long){

        val sharedPreferences = getSharedPreferences(cmChronometerPrefs, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(cmChronometerValue, getMillis)
        editor.apply()
    }

    fun MainActivity.startChronometer(){

        chronometerFunctionality.chronometerState = ChronometerState.Running

        binding.chronometer.isVisible = true
        binding.chronometer.base = SystemClock.elapsedRealtime()
        binding.chronometer.start()
    }


    fun MainActivity.continueChronometer(){

        chronometerFunctionality.chronometerState = ChronometerState.Running

        val sharedPreferences = getSharedPreferences(cmChronometerPrefs, MODE_PRIVATE)
        val getTimeElapsed = sharedPreferences.getLong(cmChronometerValue, 0)

        binding.chronometer.base = getTimeElapsed
        binding.chronometer.isVisible = true
        binding.chronometer.start()
    }

    fun MainActivity.stopChronometer(){

        chronometerFunctionality.chronometerState = ChronometerState.Stopped

        binding.chronometer.isVisible = false
        binding.chronometer.stop()
    }