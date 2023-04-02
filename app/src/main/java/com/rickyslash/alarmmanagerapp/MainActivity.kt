package com.rickyslash.alarmmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rickyslash.alarmmanagerapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

// also extending interface 'DatePickerFragment.DialogDateListener' & 'TimePickerFragment.DialogTimeListener'
class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private var binding: ActivityMainBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnOnceDate?.setOnClickListener(this)
        binding?.btnOnceTime?.setOnClickListener(this)
        binding?.btnSetOnceAlarm?.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                // showing the dialog
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }
            R.id.btn_once_time -> {
                val timePickerFragmentOne = TimePickerFragment()
                // showing the dialog
                timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            R.id.btn_set_once_alarm -> {
                val onceDate = binding?.tvOnceDate?.text.toString()
                val onceTime = binding?.tvOnceTime?.text.toString()
                val onceMessage = binding?.edtOnceMessage?.text.toString()

                // set the alarm with 'setOneTimeAlarm' method from 'AlarmReceiver'
                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage)
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // sets calendar to parameter from callback
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding?.tvOnceDate?.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        // sets calendar to parameter from callback
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_ONCE_TAG -> binding?.tvOnceTime?.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> {}
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
}

// AlarmManager give app access to do time-based operation outside app's lifecycle
// - can run intent object based on time & interval that has been set
// - can work along with broadcast receiver to run another component like service / another operation
// - alarm works outside app's lifecycle, even it's on device idle / sleep
// - main purpose is, to minimize the usage of resource & avoids the usage of timer & background service

// 2 methods of doing AlarmManager:
// - setRepeating(): task being executed once
// - setInexactRepeating(): task being executed repeatedly in not-fixed time

// alarm type (Elapsed Real Time (ERT) / Real Time Clock (RTC)):
// - without wake up (ELAPSED_REALTIME / RTC)
// - wake up (ELAPSED_REALTIME_WAKEUP / RTC_WAKEUP)

// ERT vs RTC:
// - Elapsed Realtime (ERT):
// --- Time passed throughout after Android system is being run
// --- Not depend on time zone / location
// --- Still can be run even device shut down then turned on
// --- Suitable for interval-type alarm
// - Real Time Clock (RTC)
// --- Time sets by device's clock
// --- Stops when device shut down then turned on
// --- Suitable for exact-time-type alarm

// Wakeup Behavior: Make CPU turned on, even screen is off
// - suitable for very important task

// Best practice for AlarmManager:
// - only use for local process
// - keep the alarm frequency not too short in interval
// - avoid using RTC_WAKEUP for task that's not important
// - for scheduling task that do request to server, use setInExactRepeating() & ELAPSED_REALTIME for optimized battery use

// For API 19 (KitKat) & above, the usage of repeating alarm always use InExactRepeating