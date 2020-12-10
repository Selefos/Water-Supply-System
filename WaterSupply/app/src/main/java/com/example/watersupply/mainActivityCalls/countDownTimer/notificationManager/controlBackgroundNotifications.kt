package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState


/**
 * Created by Andreas on 12/4/2020.
 */

const val startBackgroundService : String = "startBackgroundService"

    fun MainActivity.startBackgroundNotifications() {
        if(timerFunctionality.timerState == TimerState.Running && !isMyServiceRunning(this, BackGroundNotifications::class.java)) {

            val serviceIntent = Intent(this, BackGroundNotifications::class.java)
            serviceIntent.putExtra(startBackgroundService, timerFunctionality.secondsRemaining)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
    }

    fun MainActivity.stopBackgroundNotifications() {

        //uses time handle to avoid conflict with Activity Life Cycle
        val handler = Handler()
        handler.postDelayed({

        //checks if the service is running and then stops it
       if(isMyServiceRunning(this, BackGroundNotifications::class.java)) {

            val serviceIntent = Intent(this, BackGroundNotifications::class.java)
            stopService(serviceIntent)
        }
        }, 1000)
    }

    /*Prevents app-crush when the user clears the cache
    clearing cache will remove the permanent notification*/
    @Suppress("DEPRECATION")
    fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }