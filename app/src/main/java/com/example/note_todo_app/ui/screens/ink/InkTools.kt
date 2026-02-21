package com.example.note_todo_app.ui.screens.ink

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Enum for different ink tools
enum class InkTool {
    PEN,
    ERASER,
    SELECT
}

// Data class to represent a stroke with metadata
data class InkStroke(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float,
    val toolType: InkTool = InkTool.PEN
)

// Extension function to create path from points
fun List<Offset>.toPath(): Path {
    val path = Path()
    if (this.isNotEmpty()) {
        path.moveTo(this[0].x, this[0].y)
        for (i in 1 until this.size) {
            path.lineTo(this[i].x, this[i].y)
        }
    }
    return path
}

// Function to erase strokes that intersect with eraser area
fun eraseStrokes(strokes: MutableList<InkStroke>, eraseAreaCenter: Offset, eraseRadius: Float): List<InkStroke> {
    // For simplicity, we'll remove any stroke whose start or end point is within the erase radius
    // A more sophisticated implementation would check intersection of paths
    return strokes.filter { stroke ->
        val firstPoint = stroke.points.firstOrNull()
        val lastPoint = stroke.points.lastOrNull()
        
        val notErasedByFirstPoint = firstPoint?.let { 
            it.getDistanceTo(eraseAreaCenter) > eraseRadius 
        } ?: true
        
        val notErasedByLastPoint = lastPoint?.let { 
            it.getDistanceTo(eraseAreaCenter) > eraseRadius 
        } ?: true
        
        notErasedByFirstPoint && notErasedByLastPoint
    }.toList()
}

// Helper function to calculate distance between two offsets
fun Offset.getDistanceTo(other: Offset): Float {
    val dx = this.x - other.x
    val dy = this.y - other.y
    return kotlin.math.sqrt(dx * dx + dy * dy)
}

// Improved toolbar with additional features
@Composable
fun AdvancedInkToolbar(
    currentTool: InkTool,
    onToolChanged: (InkTool) -> Unit,
    currentColor: Color,
    onColorChanged: (Color) -> Unit,
    currentStrokeWidth: Float,
    onStrokeWidthChanged: (Float) -> Unit,
    onClearPressed: () -> Unit,
    onUndoPressed: () -> Unit,
    onRedoPressed: () -> Unit,
    canUndo: Boolean,
    canRedo: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tool selection buttons
        InkToolButton(
            tool = InkTool.PEN,
            isSelected = currentTool == InkTool.PEN,
            icon = Icons.Default.Create,
            onClick = { onToolChanged(InkTool.PEN) },
            contentDescription = "Pen"
        )
        
        InkToolButton(
            tool = InkTool.ERASER,
            isSelected = currentTool == InkTool.ERASER,
            icon = Icons.Default.AutoFixHigh,
            onClick = { onToolChanged(InkTool.ERASER) },
            contentDescription = "Eraser"
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
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

        Spacer(modifier = Modifier.width(8.dp))

        // Stroke width controls
        IconButton(
            onClick = { 
                onStrokeWidthChanged((currentStrokeWidth - 2f).coerceAtLeast(1f)) 
            },
            enabled = currentTool == InkTool.PEN
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease stroke width")
        }
        
        Text("${currentStrokeWidth.toInt()}", modifier = Modifier.width(30.dp))
        
        IconButton(
            onClick = { 
                onStrokeWidthChanged((currentStrokeWidth + 2f).coerceAtMost(20f)) 
            },
            enabled = currentTool == InkTool.PEN
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increase stroke width")
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Undo/Redo/Clear buttons
        IconButton(onClick = onUndoPressed, enabled = canUndo) {
            Icon(Icons.Default.Undo, contentDescription = "Undo")
        }
        
        IconButton(onClick = onRedoPressed, enabled = canRedo) {
            Icon(Icons.Default.Redo, contentDescription = "Redo")
        }
        
        IconButton(onClick = onClearPressed) {
            Icon(Icons.Default.Clear, contentDescription = "Clear canvas")
        }
    }
}

@Composable
fun InkToolButton(
    tool: InkTool,
    isSelected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                color = if (isSelected) Color.LightGray else Color.Transparent,
                shape = CircleShape
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color.DarkGray else Color.LightGray,
                shape = CircleShape
            )
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp)
        )
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
                shape = CircleShape
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color.DarkGray else Color.LightGray,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center)
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
    }
}