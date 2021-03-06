package com.ajp.timerapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ajp.timerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            while (true) {
                updateTimer()
                delay(30)
            }
        }
    }

    fun updateTimer() {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time.time
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val timeSinceMidnightMillis = currentTime - calendar.time.time
        val formattedTime = formatTime(timeSinceMidnightMillis)
        // Timer text should be set to formattedTime
        binding.timerText.text = formattedTime
    }

    // formatTime() should take a variable of type long, representing the number of milliseconds since 12am,
    // and return a string of formatted time. It should use formatHoursMinutesSecondsMillis()
    fun formatTime(time: Long): String {
        val millis = time % 1000L
        val seconds = (time / 1000L) % 60L
        val minutes = (time / (1000L * 60L)) % 60L
        val hours = (time / (1000L * 60L * 60L))
        return formatHoursMinutesSecondsMillis(hours, minutes, seconds, millis)
    }

    // formatHoursMinutesSecondsMillis() should take four variables: the number of hours, minutes,
    // seconds, and milliseconds, all of type long, and return a formatted string
    fun formatHoursMinutesSecondsMillis(hours: Long, minutes: Long, seconds: Long, millis: Long): String {

        val zeroPaddedMinutes = if (minutes < 10L) "0$minutes" else "$minutes"
        val zeroPaddedSeconds = if (seconds < 10L) "0$seconds" else "$seconds"

        return "$hours:$zeroPaddedMinutes:$zeroPaddedSeconds:$millis"

    }

    fun onButtonClick(v: View) {
        val modifiedText = modifyText(binding.timerReminderInput.text.toString())
        val reversedModifiedText = reverseString(modifiedText)
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time.time
        calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.currentHour)
        calendar.set(Calendar.MINUTE, binding.timePicker.currentMinute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val timeMillis = calendar.time.time - currentTime
        if (timeMillis > 0L) {
            setTimer(timeMillis, reversedModifiedText)
        }
    }

    // modifyText() should take a variable of type String. If that string can be interpreted as a double,
    // multiply it by two and return the string of that number. Otherwise, just return the string
    fun modifyText(x: String): String {
        var y = 0.0
        if (x.isDouble()) {
            y = x.toDouble()
            y *= 2.0
            return y.toString()
        } else {
            return x
        }
    }

    // reverseString() should take variable of type String and return the reversed version of the string
    fun reverseString(input: String): String {
        var newString = ""
        for (i in 0 until input.length) {
            newString += input.get(input.length - 1 - i)
        }
        return newString
    }

    fun setTimer(timeMillis: Long, text: String) {
        // After timeMillis milliseconds have elapsed, should print text to the log
        Timer("Ahmed's Thread").schedule(object : TimerTask() {
            override fun run() {
                Log.i("TimerApp", text)
            }
        }, timeMillis)

        // Alternate method: Handler(Looper.myLooper()!!).postDelayed({ Log.i("TimerApp", text) }, timeMillis)
    }

    fun String.isDouble(): Boolean {
        return this.toDoubleOrNull() != null
    }
}