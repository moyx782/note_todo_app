package com.example.note_todo_app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.note_todo_app.data.model.Todo
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    noteId: String?, // Optional - if provided, show only todos for this note
    navController: NavController,
    viewModel: TodoViewModel // We'll define this later
) {
    val todos by if (noteId != null) {
        viewModel.getTodosByNoteId(noteId).collectAsState(initial = emptyList())
    } else {
        viewModel.allTodos.collectAsState(initial = emptyList())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId != null) "相关待办事项" else "全部待办事项") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    // Navigate to todo editor with optional noteId
                    val route = if (noteId != null) {
                        "todo_editor?noteId=$noteId"
                    } else {
                        "todo_editor"
                    }
                    navController.navigate(route)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(todos) { todo ->
                TodoCard(
                    todo = todo,
                    onToggleComplete = {
                        viewModel.updateTodoCompletion(todo.id, !todo.isCompleted)
                    },
                    onItemClick = {
                        navController.navigate("todo_editor/${todo.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun TodoCard(
    todo: Todo,
    onToggleComplete: (Todo) -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggleComplete(todo) }
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = todo.title,
                    style = if (todo.isCompleted) MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.LineThrough
                    ) else MaterialTheme.typography.bodyLarge
                )
                
                if (todo.description.isNotEmpty()) {
                    Text(
                        text = todo.description,
                        style = if (todo.isCompleted) MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = TextDecoration.LineThrough
                        ) else MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (todo.dueDate != null) {
                    Text(
                        text = "截止日期: ${todo.dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (todo.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant
                                else if (todo.dueDate.isBefore(java.time.LocalDate.now())) 
                                    MaterialTheme.colorScheme.error 
                                else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}