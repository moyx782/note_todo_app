package com.example.note_todo_app.data.model.ink

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.time.LocalDateTime

@Entity(tableName = "ink_strokes")
data class InkStrokeEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    @ColumnInfo(name = "note_id") val noteId: String,
    @ColumnInfo(name = "page_index") val pageIndex: Int,
    @ColumnInfo(name = "stroke_data") val strokeData: String, // JSON string representing the stroke points
    @ColumnInfo(name = "color") val color: String, // Hex color string
    @ColumnInfo(name = "stroke_width") val strokeWidth: Float,
    @ColumnInfo(name = "tool_type") val toolType: String, // PEN, ERASER, etc.
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime = LocalDateTime.now()
)