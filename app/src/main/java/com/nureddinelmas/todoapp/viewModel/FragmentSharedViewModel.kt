package com.nureddinelmas.todoapp.viewModel

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.TextUtils
import android.util.Log
import android.view.ContentInfo
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.nureddinelmas.todoapp.R
import com.nureddinelmas.todoapp.model.Priority
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*

class FragmentSharedViewModel(application: Application) : AndroidViewModel(application){


    val listener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
           when(position) {
               0 ->{ (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green)) }
               1 ->{ (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow)) }
           }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    fun parsePriority(priority: String): Priority {
        return when(priority){
            "Daily" -> {
                Priority.DAILY}
            "Weekly" -> {
                Priority.WEEKLY}
            else -> Priority.DAILY
        }
    }

    fun isDataOk(title: String, description: String): Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        } else !(title.isEmpty() || description.isEmpty())
    }


    fun parsePriority (priority: Priority): Int{
        return when(priority){
            Priority.WEEKLY -> 1
            Priority.DAILY -> 0
        }

    }




}