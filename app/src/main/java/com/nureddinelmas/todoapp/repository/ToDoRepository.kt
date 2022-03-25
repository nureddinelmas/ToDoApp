package com.nureddinelmas.todoapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Update
import com.nureddinelmas.todoapp.model.ToDo
import com.nureddinelmas.todoapp.roomData.ToDoDao
import retrofit2.http.Query
import java.util.*

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDo>> = toDoDao.getAllData()

    suspend fun insertData(toDo: ToDo){
        toDoDao.insertData(toDo)
    }

    @Update
    suspend fun updateData(toDo: ToDo){
        toDoDao.updateData(toDo)
    }

    @Delete
    suspend fun deleteData(toDo: ToDo){
        toDoDao.deleteData(toDo)
    }
}

