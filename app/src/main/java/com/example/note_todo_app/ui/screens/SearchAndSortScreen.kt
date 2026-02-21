package com.example.note_todo_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.note_todo_app.data.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAndSortScreen(
    navController: NavController,
    viewModel: NoteViewModel // We'll define this later
) {
    var searchQuery by remember { mutableStateOf("") }
    
    // Sort options state
    var expanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf("按更新时间") }
    
    val sortOptions = listOf(
        "按更新时间", 
        "按创建时间", 
        "按标题", 
        "按置顶优先"
    )
    
    // Get notes based on search and sort
    val filteredNotes by if (searchQuery.isEmpty()) {
        viewModel.allNotesSorted(selectedSortOption).collectAsState(initial = emptyList())
    } else {
        viewModel.searchNotes(searchQuery).collectAsState(initial = emptyList())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("搜索与排序") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            // Search bar
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("搜索笔记") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sort selector
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedSortOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("排序方式") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    sortOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedSortOption = option
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Results list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredNotes) { note ->
                    NoteCard(
                        note = note,
                        onItemClick = {
                            navController.navigate("note_detail/${note.id}")
                        }
                    )
                }
            }
        }
    }
}