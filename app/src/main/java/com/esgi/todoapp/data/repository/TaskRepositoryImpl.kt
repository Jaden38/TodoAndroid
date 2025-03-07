package com.esgi.todoapp.data.repository

import com.esgi.todoapp.data.dao.TaskDao
import com.esgi.todoapp.data.entity.toTask
import com.esgi.todoapp.data.entity.toTaskEntity
import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { taskEntities ->
            taskEntities.map { it.toTask() }
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insert(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toTaskEntity())
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }
}