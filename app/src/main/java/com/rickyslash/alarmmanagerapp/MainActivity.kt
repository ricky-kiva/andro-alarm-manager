package com.rickyslash.alarmmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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