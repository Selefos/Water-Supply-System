package com.example.watersupply.mainActivityCalls.chronometerTimer

import android.os.SystemClock
import android.widget.Chronometer
import androidx.core.view.isVisible
import com.example.watersupply.mainActivityCalls.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Andreas on 12/5/2020.
 */


fun MainActivity.chronometerInit() {
    chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {

            val time = SystemClock.elapsedRealtime() - chronometer.base
            val h = (time / 3600000)
            val m = (time - h * 3600000) / 60000
            val s = (time - h * 3600000 - m * 60000) / 1000

            val format = String.format("%02d:%02d:%02d", h, m, s)
            chronometer.format = format
        }
}

fun MainActivity.startChronometer(){
    chronometer.isVisible = true
    chronometer.base = SystemClock.elapsedRealtime()
    chronometer.start()
}

fun MainActivity.stopChronometer(){

    chronometer.isVisible = false
    chronometer.stop()
}


