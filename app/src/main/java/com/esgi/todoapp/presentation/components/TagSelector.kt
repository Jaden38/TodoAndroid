package com.esgi.todoapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TagSelector(
    tags: Set<String>,
    selectedTag: String?,
    onTagSelected: (String?) -> Unit
) {
    if (tags.isEmpty()) return

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = "Filter by tags",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    TagChip(
                        tag = "All",
                        isSelected = selectedTag == null,
                        onClick = { onTagSelected(null) }
                    )
                }

                items(tags.toList()) { tag ->
                    TagChip(
                        tag = tag,
                        isSelected = tag == selectedTag,
                        onClick = { onTagSelected(tag) }
                    )
                }
            }
        }
    }
}
