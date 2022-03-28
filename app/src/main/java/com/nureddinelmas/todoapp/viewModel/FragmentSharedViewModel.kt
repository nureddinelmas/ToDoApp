package com.nureddinelmas.todoapp.viewModel

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
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
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.nureddinelmas.todoapp.NotifierAlarm
import com.nureddinelmas.todoapp.R
import com.nureddinelmas.todoapp.model.Priority
import com.nureddinelmas.todoapp.model.ToDo
import kotlinx.android.synthetic.main.fragment_add.*
import okhttp3.internal.notify
import java.util.*

@SuppressLint("StaticFieldLeak")
class FragmentSharedViewModel(application: Application) : AndroidViewModel(application){
   lateinit var alarmManager: AlarmManager
   val context = getApplication<Application>().applicationContext

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



    fun setAlarm(toDo: ToDo) {
        val calendar = Calendar.getInstance()
        calendar.time = toDo.reminderDate

        Log.d("alarm", parsePriority(toDo.period).toString())
        //calendar.set(Calendar.SECOND, 0)
        // calendar.set(Calendar.DAY_OF_MONTH, Calendar.SATURDAY)

        var intent = Intent(context, NotifierAlarm::class.java)
        intent.putExtra("item",toDo)
        var intent1 = PendingIntent.getBroadcast(context,toDo.id,intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        Log.d("alarm", calendar.timeInMillis.toString())
        Log.d("alarm", calendar.time.toString())


        // alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent1)


        if (parsePriority(toDo.period) == 0){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY ,intent1)

        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 7 * 24 * 60 * 60 * 1000, intent1)
        }




    }




}