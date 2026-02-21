package com.example.note_todo_app.ui.screens.ink

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import java.util.*

// Data class to represent a stroke
data class Stroke(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InkScreen(
    noteId: String?,
    navController: NavController,
    inkViewModel: InkViewModel // We'll define this later
) {
    var currentStroke by remember { mutableStateOf<Stroke?>(null) }
    var strokes by remember { mutableStateListOf<Stroke>() }
    var currentColor by remember { mutableStateOf(Color.Black) }
    var currentStrokeWidth by remember { mutableStateOf(5f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("手写笔记") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Save the ink data and return to note
                        inkViewModel.saveInkData(noteId, strokes)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.Done, contentDescription = "完成")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Toolbar for tools
            InkToolbar(
                currentColor = currentColor,
                onColorChanged = { currentColor = it },
                currentStrokeWidth = currentStrokeWidth,
                onStrokeWidthChanged = { currentStrokeWidth = it },
                onClearPressed = { 
                    strokes.clear()
                    currentStroke = null
                },
                onUndoPressed = {
                    if (strokes.isNotEmpty()) {
                        strokes.removeAt(strokes.size - 1)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Canvas for drawing
            DrawingCanvas(
                strokes = strokes,
                currentStroke = currentStroke,
                currentColor = currentColor,
                currentStrokeWidth = currentStrokeWidth,
                onNewPoint = { point ->
                    val newStroke = if (currentStroke == null) {
                        Stroke(
                            points = listOf(point),
                            color = currentColor,
                            strokeWidth = currentStrokeWidth
                        )
                    } else {
                        currentStroke!!.copy(points = currentStroke!!.points + point)
                    }
                    currentStroke = newStroke
                },
                onStrokeEnd = {
                    currentStroke?.let { stroke ->
                        strokes.add(stroke)
                        currentStroke = null
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun InkToolbar(
    currentColor: Color,
    onColorChanged: (Color) -> Unit,
    currentStrokeWidth: Float,
    onStrokeWidthChanged: (Float) -> Unit,
    onClearPressed: () -> Unit,
    onUndoPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color selection buttons
        InkColorButton(color = Color.Black, isSelected = currentColor == Color.Black) {
            onColorChanged(Color.Black)
        }
        InkColorButton(color = Color.Red, isSelected = currentColor == Color.Red) {
            onColorChanged(Color.Red)
        }
        InkColorButton(color = Color.Blue, isSelected = currentColor == Color.Blue) {
            onColorChanged(Color.Blue)
        }
        InkColorButton(color = Color.Green, isSelected = currentColor == Color.Green) {
            onColorChanged(Color.Green)
        }

        // Stroke width controls
        IconButton(onClick = { 
            onStrokeWidthChanged((currentStrokeWidth - 2f).coerceAtLeast(1f)) 
        }) {
            Text("-")
        }
        
        Text("${currentStrokeWidth.toInt()}")
        
        IconButton(onClick = { 
            onStrokeWidthChanged((currentStrokeWidth + 2f).coerceAtMost(20f)) 
        }) {
            Text("+")
        }

        // Undo button
        IconButton(onClick = onUndoPressed) {
            Text("↩️")
        }

        // Clear button
        IconButton(onClick = onClearPressed) {
            Text("🗑️")
        }
    }
}

@Composable
fun InkColorButton(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                color = if (isSelected) Color.Gray else Color.Transparent,
                shape = androidx.compose.foundation.shape.CircleShape
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color.DarkGray else Color.LightGray,
                shape = androidx.compose.foundation.shape.CircleShape
            )
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center)
                .background(
                    color = color,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
    }
}

@Composable
fun DrawingCanvas(
    strokes: List<Stroke>,
    currentStroke: Stroke?,
    currentColor: Color,
    currentStrokeWidth: Float,
    onNewPoint: (Offset) -> Unit,
    onStrokeEnd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Move -> {
                                val position = event.changes.first().position
                                onNewPoint(position)
                            }
                            PointerEventType.Release -> {
                                onStrokeEnd()
                            }
                        }
                    }
                }
            }
    ) {
        // Draw all completed strokes
        strokes.forEach { stroke ->
            drawPath(
                path = createPathFromPoints(stroke.points),
                color = stroke.color,
                style = Stroke(width = stroke.strokeWidth)
            )
        }

        // Draw current stroke being drawn
        currentStroke?.let { stroke ->
            drawPath(
                path = createPathFromPoints(stroke.points),
                color = stroke.color,
                style = Stroke(width = stroke.strokeWidth)
            )
        }
    }
}

fun createPathFromPoints(points: List<Offset>): Path {
    val path = Path()
    if (points.isNotEmpty()) {
        path.moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size) {
            path.lineTo(points[i].x, points[i].y)
        }
    }
    return path
}