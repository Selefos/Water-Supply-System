package com.example.watersupply.mainActivityCalls

import android.telephony.SmsManager
import android.widget.Toast

/**
 * Created by Andreas on 12/29/2020.
 */

    fun arduinoInitiate(){
        //receive response from ARDUINO SHIELD that the message was send and the water pump is open
    }

    fun arduinoStop(){
        //receive message from ARDUINO SHIELD that the water pump has closed after the internal arduino timer stop
    }

    fun sendSMSInitiateNoTimer() {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage("+000000000000",null,"START", null,null)
    }

    fun sendSMSDeactivateNoTimer(){
        val sms = SmsManager.getDefault()
        sms.sendTextMessage("+000000000000",null,"STOP", null,null)
    }

    fun MainActivity.sendSMSInitiateCountdown(){
        val sms = SmsManager.getDefault()
        Toast.makeText(this, timerFunctionality.convertedTimerCountToHours.toString(), Toast.LENGTH_SHORT).show()
        sms.sendTextMessage("+000000000000",null,timerFunctionality.secondsRemaining.toString(), null,null)
    }

    fun sendSMSDeactivateCountdown(){
        val sms = SmsManager.getDefault()
        sms.sendTextMessage("+000000000000",null,"PAUSE", null,null)
    }