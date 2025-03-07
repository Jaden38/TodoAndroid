package com.esgi.todoapp.domain.usecase

import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.domain.repository.TaskRepository
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.insertTask(task)
    }
}