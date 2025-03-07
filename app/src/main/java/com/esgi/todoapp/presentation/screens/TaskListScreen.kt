package com.esgi.todoapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.esgi.todoapp.domain.model.Task
import com.esgi.todoapp.presentation.components.AddEditTaskDialog
import com.esgi.todoapp.presentation.components.SearchBar
import com.esgi.todoapp.presentation.components.TagSelector
import com.esgi.todoapp.presentation.components.TaskItem
import com.esgi.todoapp.presentation.components.ThemeToggle
import com.esgi.todoapp.presentation.viewmodel.TagViewModel
import com.esgi.todoapp.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    tagViewModel: TagViewModel,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedTag by tagViewModel.selectedTag.collectAsState()
    val availableTags by tagViewModel.availableTags.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(tasks) {
        tagViewModel.updateAvailableTags(tasks)
    }

    val filteredTasks = if (selectedTag != null) {
        tasks.filter { task -> selectedTag in task.tags }
    } else {
        tasks
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "To-Do App") },
                actions = {
                    ThemeToggle(
                        isDarkTheme = isDarkTheme,
                        onToggle = onThemeToggle
                    )

                    IconButton(
                        onClick = { if (tasks.isNotEmpty()) showDeleteConfirmation = true },
                        enabled = tasks.isNotEmpty()
                    ) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) }
            )

            if (availableTags.isNotEmpty()) {
                TagSelector(
                    tags = availableTags,
                    selectedTag = selectedTag,
                    onTagSelected = { tagViewModel.selectTag(it) }
                )
            }

            if (filteredTasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when {
                            searchQuery.isNotBlank() && selectedTag != null ->
                                "Aucune tâche avec le tag '$selectedTag' et contenant '$searchQuery'"
                            searchQuery.isNotBlank() ->
                                "Aucune tâche contenant '$searchQuery'"
                            selectedTag != null && tasks.isNotEmpty() ->
                                "Aucune tâche avec le tag '$selectedTag'"
                            else -> "Aucune tâche"
                        }
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(filteredTasks) { task ->
                        TaskItem(
                            task = task,
                            onDelete = { viewModel.deleteTask(task) },
                            onEdit = { editingTask = task },
                            onTagClick = { tag -> tagViewModel.selectTag(tag) }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddEditTaskDialog(
                onDismiss = { showAddDialog = false },
                availableTags = availableTags,
                onConfirm = { task ->
                    viewModel.insertTask(task)
                    showAddDialog = false
                }
            )
        }

        editingTask?.let { task ->
            AddEditTaskDialog(
                task = task,
                availableTags = availableTags,
                onDismiss = { editingTask = null },
                onConfirm = { updatedTask ->
                    viewModel.updateTask(updatedTask)
                    editingTask = null
                }
            )
        }

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Attention") },
                text = { Text("Êtes-vous sûr de vouloir supprimer toutes les tâches ? Cette action est irréversible.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteAllTasks()
                            showDeleteConfirmation = false
                        }
                    ) {
                        Text("Confirmer")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text("Annuler")
                    }
                }
            )
        }
    }
}