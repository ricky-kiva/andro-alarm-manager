package com.rickyslash.alarmmanagerapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

// 'DatePickerDialog' is a class that will generate its own pre-defined layout
// 'DialogFragment' will ensure the 'DatePickerDialog' is displayed as modal
class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var mListener: DialogDateListener? = null

    // this called when the fragment needs to create new Dialog (by .show())
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // this will set the date of the dialog to the today's date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)

        // creates new DatePickerDialog instance that will be shown as 'modal'
        return DatePickerDialog(activity as Context, this, year, month, date)
    }

    // this called when date is selected from the dialog
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // passing the parameters to the overridden interface function (onDialogDateSet)
        mListener?.onDialogDateSet(tag, year, month, dayOfMonth)
    }

    // attaching this fragment to activity (MainActivity)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogDateListener?
    }

    // detaching this fragment
    override fun onDetach() {
        super.onDetach()
        if (mListener != null) {
            mListener = null
        }
    }

    // callback for attached activity
    interface DialogDateListener {
        fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
    }

}