package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerFunctionality
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

    //checks if the service is running and then stops it
    if(isMyServiceRunning(this, BackGroundNotifications::class.java)) {
        val serviceIntent = Intent(this, BackGroundNotifications::class.java)
        stopService(serviceIntent)
    }
}

/*Prevents app-crush when the user clears the cache
clearing cache will remove the permanent notification*/
@Suppress("DEPRECATION")
fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE))
        if (serviceClass.name == service.service.className)
            return true

    return false
}

class ControlBackgroundNotifications{

    companion object{

        val timerFunctionality = TimerFunctionality()
        fun startBackgroundElapsedNotification(context: Context, currentSeconds : Long, secondsRemaining : Long) : Long{

            val alarmRequestCode = 3

            val notificationTime = (currentSeconds + secondsRemaining) * 1000
            val intent = Intent(context, NotificationBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, alarmRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)

            //stops alarm from repeating notification on screen unlock
            if(notificationTime == 0L)
                stopBackgroundElapsedNotification(context)


            return notificationTime
        }

        fun stopBackgroundElapsedNotification(context: Context){

            //Toast.makeText(context, "alarm off", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, NotificationBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }
}