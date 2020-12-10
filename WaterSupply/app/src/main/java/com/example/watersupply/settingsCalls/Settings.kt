@file:Suppress("UNNECESSARY_SAFE_CALL")

package com.example.watersupply.settingsCalls

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.watersupply.R
import com.example.watersupply.SharedPreferencesInitialization
import com.example.watersupply.mainActivityCalls.MainActivity
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

/**
 * Created by Andreas on 11/16/2020.
 */

class Settings : AppCompatActivity() {

    val sharedPreferencesInitialization = SharedPreferencesInitialization()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        functionsCall()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle action bar item clicks here.
        when(item.itemId){
            android.R.id.home -> mainActivityTransition()
            R.id.settings -> settingsActivity()
            R.id.last_log -> Toast.makeText(this, "Last Log", Toast.LENGTH_LONG).show()
            R.id.exit -> exitApp()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun toolBarInit() {

        //val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar as Toolbar?)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

    }


    override fun onBackPressed() {
        super.onBackPressed()
        mainActivityTransition()
    }


    private fun functionsCall() {

        toolBarInit()
        init()

        darkMode()
        setDarkModePrefs()

        setTimerPrefs()
        timerOnOff()

        setTimerSeekBarPrefs()
        timerSeekBarTimeSet()

        updateViews()

    }


    private fun init() {

        sharedPreferencesInitialization.darkModeSwitch = dark_mode_switch

        sharedPreferencesInitialization.timerSwitch = timer_switch

        sharedPreferencesInitialization.timerEditTextPrefs = time_set_edit_text

        sharedPreferencesInitialization.timerSeekBar = time_seek_bar
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


    private fun settingsActivity() {
        val intent = Intent(this@Settings, Settings::class.java)
        startActivity(intent)
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