package com.nureddinelmas.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nureddinelmas.todoapp.model.ToDo
import com.nureddinelmas.todoapp.repository.ToDoRepository
import com.nureddinelmas.todoapp.roomData.ToDoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository

    val getAllData: LiveData<List<ToDo>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
    }


    fun insertData(toDo: ToDo){
        viewModelScope.launch {
            repository.insertData(toDo)
        }
    }

    fun updateData(toDo: ToDo){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateData(toDo)
        }
    }

    fun deleteData(toDo: ToDo){
        viewModelScope.launch {
            repository.deleteData(toDo)
        }
    }



}