C:.
└───todoapp
│   MainActivity.kt
│
├───data
│   ├───dao
│   │       TaskDao.kt
│   │
│   ├───database
│   │       TaskDatabase.kt
│   │
│   ├───entity
│   │       TaskEntity.kt
│   │
│   └───repository
│           TaskRepositoryImpl.kt
│
├───di
│       AppModule.kt
│       TodoApplication.kt
│
├───domain
│   ├───model
│   │       Task.kt
│   │
│   ├───repository
│   │       TaskRepository.kt
│   │
│   └───usecase
│           DeleteAllTasksUseCase.kt
│           DeleteTaskUseCase.kt
│           GetAllTasksUseCase.kt
│           InsertTaskUseCase.kt
│           TaskUseCases.kt
│           UpdateTaskUseCase.kt
│
├───presentation
│   ├───components
│   │       AddEditTaskDialog.kt
│   │       TaskItem.kt
│   │
│   ├───screens
│   │       TaskListScreen.kt
│   │
│   └───viewmodel
│           TaskViewModel.kt
│
└───ui
└───theme
Color.kt
Theme.kt
Type.kt