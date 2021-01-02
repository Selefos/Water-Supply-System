package com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class TimerExpiredReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {

        PrefUtilTimer.setTimerState(TimerState.Stopped, context)

        PrefUtilTimer.setAlarmSetTime(0, context)
    }
}