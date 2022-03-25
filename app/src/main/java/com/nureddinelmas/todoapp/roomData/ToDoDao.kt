package com.nureddinelmas.todoapp.roomData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nureddinelmas.todoapp.model.ToDo

@Dao
interface ToDoDao {

    @Query("SELECT * FROM table_todo ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDo : ToDo)

    @Update
    suspend fun updateData(toDo: ToDo)

    @Delete
    suspend fun deleteData(toDo: ToDo)



}