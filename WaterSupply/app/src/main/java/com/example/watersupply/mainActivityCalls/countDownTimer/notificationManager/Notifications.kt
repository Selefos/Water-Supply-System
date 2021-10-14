package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.watersupply.R
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.etHoursEstimated
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.etTimeEstimated
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.hoursEstimated

/**
 * Created by Andreas on 11/28/2020.
 */

    const val nfChannelId = "channel_id"
    const val nfNotificationId = 1

    fun MainActivity.createNotificationChannel() {

        val sharedPreferences = getSharedPreferences(etTimeEstimated, Context.MODE_PRIVATE)
        hoursEstimated = sharedPreferences.getString(etHoursEstimated, "").toString()

        //channel for normal notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Timer runs on background."
            val descriptionText = "The timer will stop at $hoursEstimated"

            val notificationImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(nfChannelId, name, notificationImportance).apply {
                description = descriptionText
            }
                val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
        }
    }

    fun MainActivity.sendNotification(){

        //this notification is activated if the app is open as the timer is finished
        //set notification defaults
        val soundUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification : NotificationCompat.Builder = NotificationCompat.Builder(this, nfChannelId)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("!! Timer Zeroed !!")
                .setContentText("The Timer Has Stopped.\nWater Supply Closed.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_LIGHTS)

        with(NotificationManagerCompat.from(this)){
            notify(nfNotificationId, notification.build())
        }
    }

    fun MainActivity.createElapsedNotificationChannel() {

        //channel for background notification on timer zeroed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "!! Timer Zeroed !!"
            val descriptionText = "The Timer Has Stopped.\nWater Supply Closed."

            val notificationImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(nbrNotificationChannelId, name, notificationImportance).apply {
                description = descriptionText
                enableLights(true)
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }