package com.example.watersupply.mainActivityCalls

import android.app.AlertDialog
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by Andreas on 4/7/2021.
 */

    const val SMS_SEND = 101
    const val smsSend = "smsSend"

    const val SMS_RECEIVE = 102
    const val smsReceive = "smsReceive"

    const val BATTERY_RESTRICTIONS = 103

    fun MainActivity.checkForPermission(permission: String, name: String, requestCode: Int) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(applicationContext, permission) -> {
                    //Toast.makeText(applicationContext, "permission granted", Toast.LENGTH_SHORT).show()
                }
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    fun MainActivity.checkForBatteryOptimization(){
        val powerManager: PowerManager = getSystemService(POWER_SERVICE) as PowerManager
        if(!powerManager.isIgnoringBatteryOptimizations(packageName)){
            AlertDialog.Builder(this)
                .setMessage("Battery optimization is enabled. Remove for background services activity.")
                .setTitle("WARNING")
                .setCancelable(true)
                .setPositiveButton("Ok") { _, _ ->
                    val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                    startActivityForResult(intent, BATTERY_RESTRICTIONS)
                }
                .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()}
                .create()
                .show()
        }
    }