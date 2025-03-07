package com.esgi.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.domain.usecase.TaskUseCases
import com.esgi.todoapp.util.FuzzySearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val tasks: StateFlow<List<Task>> = combine(_allTasks, _searchQuery) { tasks, query ->
        if (query.isBlank()) {
            tasks
        } else {
            FuzzySearch.searchTasks(tasks, query).map { it.first }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskUseCases.getAllTasksUseCase().collectLatest { tasksList ->
                _allTasks.value = tasksList
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
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