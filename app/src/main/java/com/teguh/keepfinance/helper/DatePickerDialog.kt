package com.teguh.keepfinance.helper

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialog: DialogFragment(), DatePickerDialog.OnDateSetListener  {

    private var itemClick: DatePickerCLickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context != null){
            itemClick = context as DatePickerCLickListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (itemClick != null)
            itemClick = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity as Context,this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        itemClick?.tanggalSelected(year,month,day)
    }

}

interface DatePickerCLickListener {
    fun tanggalSelected(year:Int, month:Int, hari:Int)
}