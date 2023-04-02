package com.rickyslash.alarmmanagerapp

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

// 'TimePickerDialog' is a class that will generate its own pre-defined layout
// 'DialogFragment' will ensure the 'TimePickerDialog' is displayed as modal
class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var mListener: DialogTimeListener? = null

    // this called when the fragment needs to create new Dialog (by .show())
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // this will set the time of the dialog to right now's time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val formatHour24 = true

        // creates new TimePickerDialog instance that will be shown as 'modal'
        return TimePickerDialog(activity, this, hour, minute, formatHour24)
    }

    // this called when time is selected from the dialog
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // passing the parameters to the overridden interface function (onDialogTimeSet)
        mListener?.onDialogTimeSet(tag, hourOfDay, minute)
    }

    // attaching this fragment to activity (MainActivity)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogTimeListener?
    }

    // detaching this fragment
    override fun onDetach() {
        super.onDetach()
        if (mListener != null) {
            mListener = null
        }
    }

    // callback for attached activity
    interface DialogTimeListener {
        fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
    }

}