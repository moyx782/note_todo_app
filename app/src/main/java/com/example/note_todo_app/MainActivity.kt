package com.example.note_todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.note_todo_app.ui.theme.NoteTodoAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppEntry()
                }
            }
        }
    }
}

private enum class AppRoute {
    HOME,
    NOTES,
    NOTE_EDITOR,
    TODOS,
    INK
}

private data class UiNote(
    val id: Int,
    val title: String,
    val content: String,
    val updatedAtMillis: Long
)

private data class UiTodo(
    val id: Int,
    val title: String,
    val isDone: Boolean
)

@Composable
private fun AppEntry() {
    var route by remember { mutableStateOf(AppRoute.HOME) }
    val notes = remember {
        mutableStateListOf(
            UiNote(
                id = 1,
                title = "Welcome Note",
                content = "Tap + to create a new note, or tap an item to edit.",
                updatedAtMillis = System.currentTimeMillis()
            )
        )
    }
    val todos = remember {
        mutableStateListOf(
            UiTodo(id = 1, title = "Sample todo item", isDone = false)
        )
    }
    var editingNoteId by remember { mutableStateOf<Int?>(null) }

    when (route) {
        AppRoute.HOME -> HomePage(
            onNotesClick = { route = AppRoute.NOTES },
            onTodosClick = { route = AppRoute.TODOS },
            onInkClick = { route = AppRoute.INK }
        )
        AppRoute.NOTES -> NotesListPage(
            notes = notes,
            onBack = { route = AppRoute.HOME },
            onAdd = {
                editingNoteId = null
                route = AppRoute.NOTE_EDITOR
            },
            onOpenNote = { noteId ->
                editingNoteId = noteId
                route = AppRoute.NOTE_EDITOR
            },
            onDeleteNote = { noteId ->
                val index = notes.indexOfFirst { it.id == noteId }
                if (index >= 0) notes.removeAt(index)
            }
        )
        AppRoute.NOTE_EDITOR -> NoteEditorPage(
            note = notes.firstOrNull { it.id == editingNoteId },
            onSave = { title, content ->
                val now = System.currentTimeMillis()
                val existingId = editingNoteId
                if (existingId == null) {
                    val nextId = (notes.maxOfOrNull { it.id } ?: 0) + 1
                    notes.add(
                        0,
                        UiNote(
                            id = nextId,
                            title = title,
                            content = content,
                            updatedAtMillis = now
                        )
                    )
                } else {
                    val index = notes.indexOfFirst { it.id == existingId }
                    if (index >= 0) {
                        notes[index] = notes[index].copy(
                            title = title,
                            content = content,
                            updatedAtMillis = now
                        )
                    }
                }
                route = AppRoute.NOTES
            },
            onBack = { route = AppRoute.NOTES }
        )
        AppRoute.TODOS -> TodosPage(
            todos = todos,
            onBack = { route = AppRoute.HOME },
            onAddTodo = { title ->
                val nextId = (todos.maxOfOrNull { it.id } ?: 0) + 1
                todos.add(0, UiTodo(id = nextId, title = title, isDone = false))
            },
            onToggleDone = { todoId ->
                val index = todos.indexOfFirst { it.id == todoId }
                if (index >= 0) todos[index] = todos[index].copy(isDone = !todos[index].isDone)
            },
            onDeleteTodo = { todoId ->
                val index = todos.indexOfFirst { it.id == todoId }
                if (index >= 0) todos.removeAt(index)
            }
        )
        AppRoute.INK -> InkPage(onBack = { route = AppRoute.HOME })
    }
}

@Composable
private fun HomePage(
    onNotesClick: () -> Unit,
    onTodosClick: () -> Unit,
    onInkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Note Todo App", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onNotesClick) { Text("Notes") }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onTodosClick) { Text("Todos") }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onInkClick) { Text("Ink") }
    }
}

@Composable
private fun NotesListPage(
    notes: List<UiNote>,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    onOpenNote: (Int) -> Unit,
    onDeleteNote: (Int) -> Unit
) {
    val formatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = onBack) { Text("Back") }
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (notes.isEmpty()) {
                Text("No notes yet. Tap + to create one.")
            } else {
                LazyColumn {
                    items(notes, key = { it.id }) { note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onOpenNote(note.id) }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row {
                                    Text(
                                        note.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Button(onClick = { onDeleteNote(note.id) }) {
                                        Text("Delete")
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = note.content.ifBlank { "(empty)" },
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Updated ${formatter.format(Date(note.updatedAtMillis))}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TodosPage(
    todos: List<UiTodo>,
    onBack: () -> Unit,
    onAddTodo: (String) -> Unit,
    onToggleDone: (Int) -> Unit,
    onDeleteTodo: (Int) -> Unit
) {
    var newTodo by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "Todos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onBack) { Text("Back") }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            OutlinedTextField(
                value = newTodo,
                onValueChange = { newTodo = it },
                label = { Text("New todo") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onAddTodo(newTodo.trim())
                    newTodo = ""
                },
                enabled = newTodo.isNotBlank()
            ) { Text("Add") }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (todos.isEmpty()) {
            Text("No todos yet.")
        } else {
            LazyColumn {
                items(todos, key = { it.id }) { todo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp)) {
                            Checkbox(
                                checked = todo.isDone,
                                onCheckedChange = { onToggleDone(todo.id) }
                            )
                            Text(
                                text = if (todo.isDone) "✓ ${todo.title}" else todo.title,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 12.dp)
                            )
                            Button(onClick = { onDeleteTodo(todo.id) }) { Text("Delete") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InkPage(onBack: () -> Unit) {
    val strokes = remember { mutableStateListOf<List<Offset>>() }
    var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "Ink",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { strokes.clear(); currentStroke = emptyList() }) { Text("Clear") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onBack) { Text("Back") }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF2F2F2))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { start -> currentStroke = listOf(start) },
                        onDrag = { change, _ ->
                            currentStroke = currentStroke + change.position
                        },
                        onDragEnd = {
                            if (currentStroke.size > 1) {
                                strokes.add(currentStroke)
                            }
                            currentStroke = emptyList()
                        },
                        onDragCancel = { currentStroke = emptyList() }
                    )
                }
        ) {
            for (stroke in strokes) {
                for (i in 1 until stroke.size) {
                    drawLine(
                        color = Color.Black,
                        start = stroke[i - 1],
                        end = stroke[i],
                        strokeWidth = 6f
                    )
                }
            }
            for (i in 1 until currentStroke.size) {
                drawLine(
                    color = Color(0xFF1565C0),
                    start = currentStroke[i - 1],
                    end = currentStroke[i],
                    strokeWidth = 6f
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Drag on the canvas to draw.")
    }
}

@Composable
private fun NoteEditorPage(
    note: UiNote?,
    onSave: (title: String, content: String) -> Unit,
    onBack: () -> Unit
) {
    var title by remember(note?.id) { mutableStateOf(note?.title ?: "") }
    var content by remember(note?.id) { mutableStateOf(note?.content ?: "") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (note == null) "New Note" else "Edit Note",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = onBack) { Text("Cancel") }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { onSave(title.trim(), content.trim()) },
                enabled = title.isNotBlank() || content.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun PlaceholderPage(title: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$title Page", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun PreviewGreeting() {
    NoteTodoAppTheme {
        AppEntry()
    }
}
