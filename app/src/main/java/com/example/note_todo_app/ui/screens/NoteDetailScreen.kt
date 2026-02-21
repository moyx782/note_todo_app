package com.example.note_todo_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.note_todo_app.data.model.Note
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String,
    navController: NavController,
    viewModel: NoteViewModel // We'll define this later
) {
    val note by viewModel.getNoteById(noteId).collectAsState(initial = null)

    note?.let { currentNote ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            text = currentNote.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { 
                            // Navigate to edit screen
                            navController.navigate("note_editor/${currentNote.id}")
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        
                        IconButton(onClick = { 
                            viewModel.deleteNote(currentNote)
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = currentNote.content,
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "创建于: ${currentNote.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "更新于: ${currentNote.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (currentNote.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "标签: ${currentNote.tags}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { 
                        // Navigate to todo list for this note
                        navController.navigate("todo_list/${currentNote.id}")
                    },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("查看相关待办事项")
                }
            }
        }
    } ?: run {
        // Show loading or error state
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("找不到笔记")
        }
    }
}