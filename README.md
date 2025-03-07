# Application To-Do avec Room et Clean Architecture

## Structure du projet

L'application suit les principes de la Clean Architecture avec une séparation claire des responsabilités:

```
└── 📁com
    └── 📁esgi
        └── 📁todoapp
            └── 📁data
                └── 📁dao
                    └── TaskDao.kt
                └── 📁database
                    └── TaskDatabase.kt
                └── 📁entity
                    └── TaskEntity.kt
                └── 📁repository
                    └── TaskRepositoryImpl.kt
            └── 📁di
                └── AppModule.kt
                └── TodoApplication.kt
            └── 📁domain
                └── 📁model
                    └── Task.kt
                └── 📁repository
                    └── TaskRepository.kt
                └── 📁usecase
                    └── DeleteAllTasksUseCase.kt
                    └── DeleteTaskUseCase.kt
                    └── GetAllTasksUseCase.kt
                    └── InsertTaskUseCase.kt
                    └── TaskUseCases.kt
                    └── UpdateTaskUseCase.kt
            └── MainActivity.kt
            └── 📁presentation
                └── 📁components
                    └── AddEditTaskDialog.kt
                    └── TaskItem.kt
                └── 📁screens
                    └── TaskListScreen.kt
                └── 📁viewmodel
                    └── TaskViewModel.kt
            └── 📁ui
                └── 📁theme
                    └── Color.kt
                    └── Theme.kt
                    └── Type.kt
```