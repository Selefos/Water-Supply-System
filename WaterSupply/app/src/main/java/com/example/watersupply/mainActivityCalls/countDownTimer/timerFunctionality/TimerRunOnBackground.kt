package com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager.TimerExpiredReceiver

import java.util.*

/**
 * Created by Andreas on 11/27/2020.
 */

class TimerRunOnBackground {


    companion object{

        fun setAlarm(context: Context, currentSeconds : Long, secondsRemaining : Long) : Long{
            val wakeTime = (currentSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0,intent,0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeTime,pendingIntent)
            PrefUtil.setAlarmSetTime(currentSeconds, context)
            return wakeTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0,intent,0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0,context)
        }

        val currentSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

    }

}