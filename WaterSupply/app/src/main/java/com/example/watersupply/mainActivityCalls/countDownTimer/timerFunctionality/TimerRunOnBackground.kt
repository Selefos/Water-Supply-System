package com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.view.isVisible
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.settingsCalls.Settings
import java.time.LocalTime

import java.util.*

/**
 * Created by Andreas on 11/27/2020.
 */

const val etTimeEstimated : String = "estimated_time"

const val etHoursEstimated : String = "hours_estimate"
var hoursEstimated = ""

class TimerRunOnBackground {

    companion object{

        fun setAlarm(context: Context, currentSeconds : Long, secondsRemaining : Long) : Long{

            //make timer run after app is killed
            val wakeTime = (currentSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0,intent,0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeTime,pendingIntent)
            PrefUtilTimer.setAlarmSetTime(currentSeconds, context)
            return wakeTime
        }

        fun removeAlarm(context: Context){

            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtilTimer.setAlarmSetTime(0,context)
        }


        val currentSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

        fun MainActivity.savePrefsEstimatedClockStopTimeOnMainActivity(){

            //get estimated time where timer stops and inform the user
            val cal = Calendar.getInstance()
            val currentHour = cal.get(Calendar.HOUR_OF_DAY)
            val currentMinutes: Int = cal.get(Calendar.MINUTE)

            //convert calendar to local time in order to be able to adjust hours and minutes
            val localTime = LocalTime.of(currentHour, currentMinutes)
            val updateTime = localTime.plusHours(sharedPreferencesInitialization.timerSeekBarValueOutput.toLong())
            val passUpdatedTime = updateTime.toString()

            //save the adjustments to sharedPreferences
            val sharedPreferences = getSharedPreferences(etTimeEstimated, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(etHoursEstimated,passUpdatedTime)
            editor.apply()
        }

        fun Settings.savePrefsEstimatedClockStopTimeOnSettingsActivity(){

            //get estimated time where timer stops and inform the user
            val cal = Calendar.getInstance()
            val currentHour = cal.get(Calendar.HOUR_OF_DAY)
            val currentMinutes: Int = cal.get(Calendar.MINUTE)

            //pass calendar to local time in order to be able to adjust hours and minutes
            val localTime = LocalTime.of(currentHour, currentMinutes)
            val updateTime = localTime.plusHours(sharedPreferencesInitialization.timerSeekBarValueInput.toLong()+1)
            val passUpdatedTime = updateTime.toString()

            //save the adjustments to sharedPreferences
            val sharedPreferences = getSharedPreferences(etTimeEstimated, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(etHoursEstimated,passUpdatedTime)
            editor.apply()
        }

        @SuppressLint("SetTextI18n")
        fun MainActivity.loadPrefsEstimatedClockStopTime(){

            //load adjustments from sharedPreferences
            val sharedPreferences = getSharedPreferences(etTimeEstimated, Context.MODE_PRIVATE)
            hoursEstimated = sharedPreferences.getString(etHoursEstimated, "").toString()

            //display loaded data
            binding.clockStopTime.text = "The timer will stop at: $hoursEstimated"
        }

        fun MainActivity.timeMessage(){

            if(timerFunctionality.timerState == TimerState.Running)
                binding.clockStopTime.isVisible = true

            if(timerFunctionality.timerState == TimerState.Stopped || timerFunctionality.timerState == TimerState.Paused)
                binding.clockStopTime.isVisible = false
        }
    }
}