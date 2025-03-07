package com.esgi.todoapp.domain.usecase

import javax.inject.Inject

data class TaskUseCases @Inject constructor(
    val getAllTasksUseCase: GetAllTasksUseCase,
    val insertTaskUseCase: InsertTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val deleteAllTasksUseCase: DeleteAllTasksUseCase
)