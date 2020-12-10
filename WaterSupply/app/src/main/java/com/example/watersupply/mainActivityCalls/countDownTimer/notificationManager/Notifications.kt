package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.watersupply.R
import com.example.watersupply.mainActivityCalls.MainActivity

/**
 * Created by Andreas on 11/28/2020.
 */

const val nfChannelId = "channel_id"
const val notificationId = 1






     fun MainActivity.createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = " Notification Description"
            val notificationImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(nfChannelId, name, notificationImportance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun MainActivity.sendNotification(){
        val soundUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification : NotificationCompat.Builder = NotificationCompat.Builder(this, nfChannelId)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("!! Timer Zeroed !!")
                .setContentText("The Timer Has Stopped.\nWater Supply Closed.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)


        with(NotificationManagerCompat.from(this)){
            notify(notificationId, notification.build())
        }
    }






