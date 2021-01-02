package com.example.watersupply.mainActivityCalls.countDownTimer.notificationManager

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.watersupply.mainActivityCalls.MainActivity


/**
 * Created by Andreas on 12/22/2020.
 */

    const val IGNORE_BATTERY_RESTRICTIONS_PERMISSION_CODE = 7

    fun MainActivity.checkForPermissions(permission: String, name: String, requestCode: Int){

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
           when{
               ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED ->{
                   Toast.makeText(applicationContext,"$name permission granted", Toast.LENGTH_SHORT).show()
               }
               shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)
               else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
           }
       }
    }

    fun MainActivity.showDialog(permission: String, name: String, requestCode: Int){

        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access $name is required for this app")
            setTitle("Permission required")
            setPositiveButton("ALLOW"){_, _ -> ActivityCompat.requestPermissions(MainActivity(), arrayOf(permission), requestCode) }
        }

        val dialog = builder.create()
        dialog.show()
    }