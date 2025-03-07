package com.esgi.todoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esgi.todoapp.data.dao.TaskDao
import com.esgi.todoapp.data.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}