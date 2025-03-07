package com.esgi.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.domain.usecase.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskUseCases.getAllTasksUseCase().collectLatest { tasksList ->
                _tasks.value = tasksList
            }
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.insertTaskUseCase(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.updateTaskUseCase(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.deleteTaskUseCase(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            taskUseCases.deleteAllTasksUseCase()
        }
    }
}