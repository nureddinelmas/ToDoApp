package com.nureddinelmas.todoapp.utils


import android.app.*
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.*
import androidx.room.TypeConverter
import com.nureddinelmas.todoapp.R
import com.nureddinelmas.todoapp.model.Priority
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromPeriod(priority: Priority): String {
        return priority.name
    }

    fun toPeriod(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}

class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}