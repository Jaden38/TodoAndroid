package com.esgi.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagViewModel @Inject constructor() : ViewModel() {
    private val _selectedTag = MutableStateFlow<String?>(null)
    val selectedTag: StateFlow<String?> = _selectedTag

    private val _availableTags = MutableStateFlow<Set<String>>(emptySet())
    val availableTags: StateFlow<Set<String>> = _availableTags

    fun updateAvailableTags(tasks: List<com.esgi.todoapp.domain.model.Task>) {
        val allTags = tasks.flatMap { it.tags }.toSet()
        _availableTags.value = allTags
    }

    fun selectTag(tag: String?) {
        _selectedTag.value = tag
    }

    fun clearTagFilter() {
        _selectedTag.value = null
    }
}