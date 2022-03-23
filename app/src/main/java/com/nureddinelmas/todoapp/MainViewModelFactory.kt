package com.nureddinelmas.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nureddinelmas.todoapp.repository.Repository
import com.nureddinelmas.todoapp.viewModel.MainViewModel

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return MainViewModel(repository) as T
    }

}