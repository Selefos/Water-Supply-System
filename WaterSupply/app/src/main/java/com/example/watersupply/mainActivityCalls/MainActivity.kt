package com.example.watersupply.mainActivityCalls

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.watersupply.R
import com.example.watersupply.SharedPreferencesInitialization
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.currentSeconds
import com.example.watersupply.mainActivityCalls.chronometerTimer.chronometerInit
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.activateTimerAnimation
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.initTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.storePreviousTime
import com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager.*
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.timerAnimationUp
import com.example.watersupply.settingsCalls.Settings
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.PrefUtil
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerFunctionality
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Andreas on 11/16/2020.
 */

/**
 * TODO Make Regular Timer, buttons on infinite cycle,
 * TODO API for connection to Arduino, send and receive data.
 * */


open class MainActivity : AppCompatActivity(){

    //region INSTANTIATE CLASSES

    val sharedPreferencesInitialization = SharedPreferencesInitialization()
    val timerFunctionality = TimerFunctionality()
    val prefUtil = PrefUtil()

    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Main","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        functionsCall()
    }

    override fun onStart() {
        Log.e("Main","onStart")
        super.onStart()

        stopBackgroundNotifications()
    }

    override fun onResume() {
        Log.e("Main","onResume")
        super.onResume()

        storePreviousTime()

        initTimer()

        activateTimerAnimation()

        TimerRunOnBackground.removeAlarm(this)

        instFiniteButtonsAfterResume()
        instInfiniteButtonsAfterResume()


    }

    override fun onPause() {
        Log.e("Main","onPause")
        super.onPause()

        if(timerFunctionality.timerState == TimerState.Running) {
            timerFunctionality.countDownTimer.cancel()
            //runs on background
            val wakeUpTimer = TimerRunOnBackground.setAlarm(this, currentSeconds, timerFunctionality.secondsRemaining)
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerFunctionality.timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(timerFunctionality.secondsRemaining, this)
        PrefUtil.setTimerState(timerFunctionality.timerState, this)

    }

    override fun onStop() {
        Log.e("Main","onStop")
        super.onStop()
        startBackgroundNotifications()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle action bar item clicks here.
        when (item.itemId) {
            R.id.settings -> settingsTransition()
            R.id.last_log -> Toast.makeText(this, "Last Log", Toast.LENGTH_LONG).show()
            R.id.exit -> exitApp()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun toolBarInit() {
        setSupportActionBar(toolbar as Toolbar?)
    }


    override fun onBackPressed() {
       exitApp()
    }


    private fun functionsCall() {

        createNotificationChannel()

        toolBarInit()


        loadDarkModePrefs()

        loadTimerPrefs()

        loadTimerSeekBarPrefs()


        instButtonBaseTimerChoice()


        chronometerInit()

    }

    private fun settingsTransition() {

        val intent = Intent(this@MainActivity, Settings::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun exitApp(){
        AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                .create()
                .show()
    }
}
