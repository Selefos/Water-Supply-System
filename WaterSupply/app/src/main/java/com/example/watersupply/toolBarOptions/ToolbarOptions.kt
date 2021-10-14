package com.example.watersupply.toolBarOptions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.settingsCalls.Settings
import java.util.*

/**
 * Created by Andreas on 12/15/2020.
 */

val lastLogPrefsInit = LastLogPrefsInit()

var infoDialogCheck = 0
const val startupInfoDialogPrefs = "info_prefs"
const val startupInfoDialogID = "info_value"
var startupInfoDialogValue = 0

const val runsOnBackgroundMessage = "This application uses functions that run on the background, even if the user stopped it. When in idle state, the Android OS may stop " +
        "the background process of this application. Please remove Battery Restrictions for this app."

fun MainActivity.toolBarInit() {
    setSupportActionBar(binding.toolbar.root)
}


fun lastLog(context: Context){

    /**
     * Get system date and time
     * Saved in SharedPreferences
     * And Display on Menu Item click
     * */

    val lastLogDate = lastLogPrefsInit.lastLogDate
    val lastLogTime = lastLogPrefsInit.lastLogTime
    val lastLogActivityTimer = lastLogPrefsInit.lastLogActivityTimer
    var pluralCheck = "hours"

    if(lastLogActivityTimer == 1L)
        pluralCheck = "hour"

    AlertDialog.Builder(context)
        .setMessage("Last Log Details.\n\nOccurred on: $lastLogDate\nTime: $lastLogTime\nActivity Time: $lastLogActivityTimer $pluralCheck")
        .setCancelable(true)
        .setNegativeButton("OK") { dialog, _ -> dialog.cancel() }
        .create()
        .show()
}


fun appInfo(context: Context){

    AlertDialog.Builder(context)
        .setMessage(runsOnBackgroundMessage)
        .setCancelable(true)
        .setNegativeButton("OK") { dialog, _ -> //saveInfoDisplayDialogPreferences(context)
            dialog.cancel() }
        .create()
        .show()
}


//    fun saveInfoDisplayDialogPreferences(context: Context){
//
//        if(startupInfoDialogValue == 0){
//            infoDialogCheck = 1
//
//            val sharedPreferences = context.getSharedPreferences(startupInfoDialogPrefs, Context.MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//            editor.putInt(startupInfoDialogID, infoDialogCheck)
//            editor.apply()
//        }
//    }
//
//    fun MainActivity.loadInfoDisplayDialogPreferences(){
//        val sharedPreferences = getSharedPreferences(startupInfoDialogPrefs, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        startupInfoDialogValue = sharedPreferences.getInt(startupInfoDialogID, infoDialogCheck)
//        editor.apply()
//
//        if(startupInfoDialogValue == 0)
//            appInfo(this)
//    }


fun MainActivity.settingsTransition() {

    val intent = Intent(this, Settings::class.java)
    startActivity(intent)
    this.finish()
}


fun Settings.toolBarInit() {

    setSupportActionBar(binding.toolbar.root)
    Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = "Settings"
}


fun Settings.settingsActivity() {
    val intent = Intent(this, Settings::class.java)
    startActivity(intent)
}