
package com.example.watersupply.settingsCalls

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.example.watersupply.R

import com.example.watersupply.databinding.ActivitySettingsBinding
import com.example.watersupply.mainActivityCalls.MainActivity
import com.example.watersupply.mainActivityCalls.chronometerTimer.ChronometerFunctionality
import com.example.watersupply.mainActivityCalls.chronometerTimer.ChronometerState
import com.example.watersupply.mainActivityCalls.chronometerTimer.PrefUtilChronometer
import com.example.watersupply.toolBarOptions.*

import java.util.*

/**
 * Created by Andreas on 11/16/2020.
 */

class Settings : AppCompatActivity() {

    val sharedPreferencesInitialization = com.example.watersupply.SharedPreferencesInitialization()
    private val chronometerFunctionality = ChronometerFunctionality()

    lateinit var binding: ActivitySettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        functionsCall()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //set menu item and deem them clickable
        when(item.itemId){
            android.R.id.home -> mainActivityTransition()
            R.id.settings -> settingsActivity()
            R.id.last_log -> lastLog(this)
            R.id.app_info -> appInfo(this)
            R.id.exit -> exitDialog()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        mainActivityTransition()
    }


    private fun functionsCall() {

        toolBarInit()
        init()

        darkMode()
        loadDarkModePrefs()

        loadTimerPrefs()
        timerOnOff()

        loadTimerSeekBarPrefs()
        timerSeekBarTimeSet()

        updateViews()
    }


    private fun init() {

        sharedPreferencesInitialization.darkModeSwitch = binding.darkModeSwitch

        sharedPreferencesInitialization.timerSwitch = binding.timerSwitch

        sharedPreferencesInitialization.timerEditTextPrefs = binding.timeSetEditText

        sharedPreferencesInitialization.timerSeekBar = binding.timeSeekBar
        sharedPreferencesInitialization.timerSeekBar.isEnabled = false
    }


    private fun updateViews() {

        sharedPreferencesInitialization.darkModeSwitch.isChecked = sharedPreferencesInitialization.darkModeOnOff
        sharedPreferencesInitialization.timerSwitch.isChecked = sharedPreferencesInitialization.timerOn

        if(sharedPreferencesInitialization.timerSeekBar.progress == 0)
            sharedPreferencesInitialization.timerSeekBar.progress = 1

        sharedPreferencesInitialization.timerSeekBar.progress = sharedPreferencesInitialization.timerSeekBarView.toInt()
    }


    private fun mainActivityTransition() {

        val intent = Intent(this@Settings, MainActivity::class.java)
        this.finish()
        startActivity(intent)
    }


    private fun waterSupplyIsOpenReminder(){

        AlertDialog.Builder(this)
                .setMessage("Watter supply is open. Please remember to open the app and close it, if you choose to leave now.")
                .setCancelable(true)
                .setPositiveButton("Close Watter Supply"){_, _ -> mainActivityTransition() }
                .setNegativeButton("Exit") { _, _ -> finish() }
                .create()
                .show()
    }


    private fun exitDialog(){
        chronometerFunctionality.chronometerState = PrefUtilChronometer.getChronometerState(this)

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