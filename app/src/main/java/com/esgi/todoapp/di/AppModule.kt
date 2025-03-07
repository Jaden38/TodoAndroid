package com.esgi.todoapp.di

import android.content.Context
import androidx.room.Room
import com.esgi.todoapp.data.dao.TaskDao
import com.esgi.todoapp.data.database.TaskDatabase
import com.esgi.todoapp.data.repository.TaskRepositoryImpl
import com.esgi.todoapp.domain.repository.TaskRepository
import com.esgi.todoapp.domain.usecase.DeleteAllTasksUseCase
import com.esgi.todoapp.domain.usecase.DeleteTaskUseCase
import com.esgi.todoapp.domain.usecase.GetAllTasksUseCase
import com.esgi.todoapp.domain.usecase.InsertTaskUseCase
import com.esgi.todoapp.domain.usecase.TaskUseCases
import com.esgi.todoapp.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getAllTasksUseCase = GetAllTasksUseCase(repository),
            insertTaskUseCase = InsertTaskUseCase(repository),
            updateTaskUseCase = UpdateTaskUseCase(repository),
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            deleteAllTasksUseCase = DeleteAllTasksUseCase(repository)
        )
    }
}