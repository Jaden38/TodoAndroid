package com.esgi.todoapp.domain.usecase

import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return taskRepository.getAllTasks()
    }
}