package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.watersupply.R
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.etHoursEstimated
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.etTimeEstimated
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.hoursEstimated

/**
 * Created by Andreas on 11/28/2020.
 */

class BackGroundNotifications : Service() {

    private var seTAG = "Timer"

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(seTAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)

        //pass pending intent to start foreground service
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0)

        val notification = sendNotification(pendingIntent)
        startForeground(nfNotificationId, notification)

        return START_STICKY
    }

    override fun onCreate() {
        Log.e(seTAG, "onCreate")
        super.onCreate()

    }

    override fun onDestroy() {
        Log.e(seTAG, "onDestroy")
        super.onDestroy()
    }


    private fun sendNotification(pendingIntent: PendingIntent) : Notification{

        val sharedPreferences = getSharedPreferences(etTimeEstimated, Context.MODE_PRIVATE)
        hoursEstimated = sharedPreferences.getString(etHoursEstimated, "").toString()

        val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            return NotificationCompat.Builder(this, nfChannelId)
                    .setSmallIcon(R.drawable.ic_clock)
                    .setContentTitle("Timer runs on background.")
                    .setContentText("The timer will stop at $hoursEstimated")
                    .setLocalOnly(true)
                    .setOngoing(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setSound(soundUri)
                    .build()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.e(seTAG, "onBind")
        return null
    }
}