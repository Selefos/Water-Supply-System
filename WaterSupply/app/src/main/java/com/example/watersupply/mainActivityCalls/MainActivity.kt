package com.example.watersupply.mainActivityCalls

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.watersupply.R
import com.example.watersupply.databinding.ActivityMainBinding
import com.example.watersupply.mainActivityCalls.chronometerTimer.*
import com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager.*
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.activateTimerAnimation
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.initTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.processTimerUI.storePreviousTime
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.PrefUtilTimer
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerFunctionality
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.currentSeconds
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.loadPrefsEstimatedClockStopTime
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerRunOnBackground.Companion.timeMessage
import com.example.watersupply.mainActivityCalls.countDownTimer.timerFunctionality.TimerState
import com.example.watersupply.toolBarOptions.*

/**
 * Created by Andreas on 11/16/2020.
 */

class MainActivity : AppCompatActivity(){

    //region INSTANTIATE CLASSES

    val sharedPreferencesInitialization = com.example.watersupply.SharedPreferencesInitialization()
    val timerFunctionality = TimerFunctionality()
    val chronometerFunctionality = ChronometerFunctionality()
    val prefUtil = PrefUtilTimer()
    //endregion

    //set views
    lateinit var binding: ActivityMainBinding
    private var checkForPermissions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Main", "onCreate")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        functionsCall()
    }

    override fun onStart() {
        Log.e("Main", "onStart")
        super.onStart()
        //stopBackgroundNotifications()
    }

    override fun onResume() {
        Log.e("Main", "onResume")
        super.onResume()

        storePreviousTime()

        initTimer()

        activateTimerAnimation()

        instantiateTimerButtonsAfterResume()
        instantiateChronometerButtonsAfterResume()


        TimerRunOnBackground.removeAlarm(this)
        ControlBackgroundNotifications.stopBackgroundElapsedNotification(this)

        loadPrefsEstimatedClockStopTime()
        timeMessage()
    }

    override fun onPause() {
        Log.e("Main", "onPause")
        super.onPause()

        if(timerFunctionality.timerState == TimerState.Running) {
            timerFunctionality.countDownTimer.cancel()

            //runs on background
            TimerRunOnBackground.setAlarm(this, currentSeconds, timerFunctionality.secondsRemaining)
            ControlBackgroundNotifications.startBackgroundElapsedNotification(this, currentSeconds, timerFunctionality.secondsRemaining)
        }

        PrefUtilTimer.setPreviousTimerLengthSeconds(timerFunctionality.timerLengthSeconds, this)
        PrefUtilTimer.setSecondsRemaining(timerFunctionality.secondsRemaining, this)
        PrefUtilTimer.setTimerState(timerFunctionality.timerState, this)
        PrefUtilChronometer.setChronometerState(chronometerFunctionality.chronometerState, this)
    }

    override fun onStop() {
        Log.e("Main", "onStop")
        super.onStop()

        saveLastLogDetails()
        startBackgroundNotifications()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.e("Main", "onCreateOptionsMenu")

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("Main", "onOptionsItemSelected")

        //set menu item and deem them clickable
        when (item.itemId) {
            R.id.settings -> settingsTransition()
            R.id.last_log -> lastLog(this)
            R.id.app_info -> appInfo(this)
            R.id.exit -> exitDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        exitDialog()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck() {
            checkForPermissions =
                !(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            onPermissionButtons()
        }

        when(requestCode){
            SMS_SEND -> innerCheck()
            SMS_RECEIVE -> innerCheck()
        }
    }

    @CallSuper
    override fun onActivityResult(requestCode : Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == BATTERY_RESTRICTIONS)
            checkForBatteryOptimization()
    }

    private fun functionsCall() {


        loadDarkModePrefs()
        stopBackgroundNotifications()
        loadTimerSeekBarPrefs()
        loadTimerPrefs()
        loadLastLogDetails()
        //loadInfoDisplayDialogPreferences()

        createNotificationChannel()
        createElapsedNotificationChannel()

        toolBarInit()

        instButtonBaseTimerChronometerChoice()

        chronometerInit()

        checkForPermission(Manifest.permission.SEND_SMS, smsSend, SMS_SEND)
        checkForPermission(Manifest.permission.RECEIVE_SMS, smsReceive, SMS_RECEIVE)
        checkForBatteryOptimization()
    }


    private fun onPermissionButtons(){
        if(!checkForPermissions){
            binding.buttonTimerOnInfinite.isEnabled = false
            binding.buttonTimerOnFinite.isEnabled = false
        } else{
            binding.buttonTimerOnInfinite.isEnabled = true
            binding.buttonTimerOnFinite.isEnabled = true
        }
    }

    private fun waterSupplyIsOpenReminder(){

        AlertDialog.Builder(this)
            .setMessage("Watter supply is open. Please remember to open the app and close it, if you choose to leave now.")
            .setCancelable(true)
            .setPositiveButton("Close Watter Supply"){_, _ -> buttonChronometerOffInfiniteInit()
                stopChronometer()}
            .setNegativeButton("Exit") { _, _ -> finish() }
            .create()
            .show()
    }

    private fun exitDialog(){
        if(chronometerFunctionality.chronometerState == ChronometerState.Running)
            waterSupplyIsOpenReminder()
        else
            exitApp()

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