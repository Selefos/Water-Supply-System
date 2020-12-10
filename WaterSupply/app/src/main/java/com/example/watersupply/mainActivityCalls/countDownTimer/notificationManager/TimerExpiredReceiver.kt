package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.PrefUtil
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState


class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //TODO: SHOW NOTIFICATION

        PrefUtil.setTimerState(TimerState.Stopped, context)

        PrefUtil.setAlarmSetTime(0, context)

    }

}