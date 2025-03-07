package com.esgi.todoapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.presentation.components.AddEditTaskDialog
import com.esgi.todoapp.presentation.components.TaskItem
import com.esgi.todoapp.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel
) {
    val tasks by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "To-Do App") },
                actions = {
                    IconButton(onClick = { viewModel.deleteAllTasks() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Supprimer tout"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter une tâche"
                )
            }
        }
    ) { paddingValues ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Aucune tâche")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onDelete = { viewModel.deleteTask(task) },
                        onEdit = { editingTask = task }
                    )
                }
            }
        }

        if (showAddDialog) {
            AddEditTaskDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { task ->
                    viewModel.insertTask(task)
                    showAddDialog = false
                }
            )
        }

        editingTask?.let { task ->
            AddEditTaskDialog(
                task = task,
                onDismiss = { editingTask = null },
                onConfirm = { updatedTask ->
                    viewModel.updateTask(updatedTask)
                    editingTask = null
                }
            )
        }
    }
}