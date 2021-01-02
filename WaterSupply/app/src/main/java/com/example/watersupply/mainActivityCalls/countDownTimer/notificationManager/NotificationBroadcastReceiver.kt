package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.annotation.SuppressLint
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.watersupply.R

/**
 * Created by Andreas on 12/16/2020.
 */

const val nbrNotificationChannelId : String = "elapsed_notification"
const val nbrNotificationId = 2

class NotificationBroadcastReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {

        //set notification defaults
        val soundUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification : NotificationCompat.Builder = NotificationCompat.Builder(context, nbrNotificationChannelId)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("!! Timer Zeroed !!")
                .setContentText("The Timer Has Stopped.\nWater Supply Closed.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_LIGHTS)

        with(NotificationManagerCompat.from(context)){
            notify(nbrNotificationId, notification.build())
        }
    }
}