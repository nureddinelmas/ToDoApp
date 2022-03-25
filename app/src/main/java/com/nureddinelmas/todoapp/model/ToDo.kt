package com.nureddinelmas.todoapp.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*


@Entity(tableName = "table_todo")
@Parcelize
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var period: Priority,
    var reminderDate: Date?,
    var description: String
        ): Parcelable

