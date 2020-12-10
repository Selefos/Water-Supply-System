package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.watersupply.R
import com.example.watersupply.mainActivityCalls.MainActivity
import java.util.*



/**
 * Created by Andreas on 11/28/2020.
 */

class BackGroundNotifications : Service() {

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var seTAG = "Timer"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(seTAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0)

        //get secondsRemaining via Intent
        val secondsRemainingWhileAppKilled = intent!!.getLongExtra(startBackgroundService, -1)
        val notification = sendNotification(pendingIntent)
        startForeground(1, notification)
        startTimer(secondsRemainingWhileAppKilled, pendingIntent)

        return START_STICKY
    }

    override fun onCreate() {
        Log.e(seTAG, "onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.e(seTAG, "onDestroy")
        stopTimerTask()
        super.onDestroy()
    }


    private fun startTimer(secondsRemaining: Long, pendingIntent: PendingIntent)  {
        //schedule the timer, gets the seconds that remain in the timer
        //and schedules the notification accordingly

        //set a new Timer
        timer = Timer()

        //initialize the TimerTask's job
        initializeTimerTask(pendingIntent)

        timer!!.schedule(timerTask, secondsRemaining * 1000, 1000)

    }

    private fun stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }


    val handler: Handler = Handler()
    private fun initializeTimerTask(pendingIntent: PendingIntent) {
        timerTask = object : TimerTask() {
            override fun run() {

                //sends notification when the timer is finished
                //use a handler to run a toast that shows the current timestamp
                handler.post {
                    val notification = sendNotificationOnTimerFinished(pendingIntent)
                    startForeground(1, notification)

                }
            }
        }
    }

    private fun sendNotification(pendingIntent: PendingIntent) : Notification{
        val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            return NotificationCompat.Builder(this, nfChannelId)
                    .setSmallIcon(R.drawable.ic_clock)
                    .setContentTitle("Timer runs on background")
                    .setContentText("Waiting for timer to stop.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setSound(soundUri)
                    .build()
    }

    private fun sendNotificationOnTimerFinished(pendingIntent: PendingIntent) : Notification{
        val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            return NotificationCompat.Builder(this, nfChannelId)
                    .setSmallIcon(R.drawable.ic_clock)
                    .setContentTitle("!! Timer Zeroed !!")
                    .setContentText("The Timer Has Stopped.\nWater Supply Closed.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setSound(soundUri)
                    .build()

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

