package com.esgi.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.esgi.todoapp.presentation.screens.TaskListScreen
import com.esgi.todoapp.presentation.viewmodel.TagViewModel
import com.esgi.todoapp.presentation.viewmodel.TaskViewModel
import com.esgi.todoapp.presentation.viewmodel.ThemeViewModel
import com.esgi.todoapp.ui.theme.TodoDamienNithardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            TodoDamienNithardTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val taskViewModel: TaskViewModel = hiltViewModel()
                    val tagViewModel: TagViewModel = hiltViewModel()

                    TaskListScreen(
                        viewModel = taskViewModel,
                        tagViewModel = tagViewModel,
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = themeViewModel::toggleTheme
                    )
                }
            }
        }
    }
}