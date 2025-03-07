package com.esgi.todoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esgi.todoapp.domain.model.Task
import java.util.Date

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val creationDate: Long,
    val tags: String = ""
)

fun TaskEntity.toTask(): Task {
    val tagList = if (this.tags.isNotEmpty()) {
        this.tags.split(",").map { it.trim() }
    } else {
        emptyList()
    }

    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        creationDate = Date(this.creationDate),
        tags = tagList
    )
}

fun Task.toTaskEntity(): TaskEntity {
    val tagsString = this.tags.joinToString(",")

    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        creationDate = this.creationDate.time,
        tags = tagsString
    )
}