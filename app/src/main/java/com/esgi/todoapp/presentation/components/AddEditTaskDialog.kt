package com.esgi.todoapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esgi.todoapp.domain.model.Task
import java.util.Date

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditTaskDialog(
    task: Task? = null,
    availableTags: Set<String> = emptySet(),
    onDismiss: () -> Unit,
    onConfirm: (Task) -> Unit
) {
    val isEditing = task != null
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var newTag by remember { mutableStateOf("") }

    val initialTags = remember {
        if (task != null && task.tags.isNotEmpty()) {
            task.tags
        } else {
            emptyList<String>()
        }
    }
    val tags = remember { mutableStateListOf<String>().apply { addAll(initialTags) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (isEditing) "Modifier la tâche" else "Ajouter une tâche") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Titre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (tags.isNotEmpty()) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.forEach { tag ->
                            TagChip(
                                tag = tag,
                                isSelected = true,
                                onClick = {

                                    val tagToRemove = tag
                                    tags.remove(tagToRemove)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                val filteredAvailableTags = availableTags.filter { it !in tags }
                if (filteredAvailableTags.isNotEmpty()) {
                    Text(
                        text = "Suggested tags:",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filteredAvailableTags.forEach { tag ->
                            TagChip(
                                tag = tag,
                                isSelected = false,
                                onClick = { tags.add(tag) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = newTag,
                        onValueChange = { newTag = it },
                        label = { Text("Nouveau tag") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (newTag.isNotBlank() && newTag !in tags) {
                                tags.add(newTag)
                                newTag = ""
                            }
                        },
                        enabled = newTag.isNotBlank() && newTag !in tags
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Ajouter un tag"
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newTask = if (isEditing && task != null) {
                        task.copy(title = title, description = description, tags = tags.toList())
                    } else {
                        Task(
                            title = title,
                            description = description,
                            creationDate = Date(),
                            tags = tags.toList()
                        )
                    }
                    onConfirm(newTask)
                },
                enabled = title.isNotBlank()
            ) {
                Text(text = if (isEditing) "Modifier" else "Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}