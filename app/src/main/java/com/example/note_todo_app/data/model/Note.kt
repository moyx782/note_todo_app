package com.example.note_todo_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val tags: String = "", // 逗号分隔的标签列表
    val isPinned: Boolean = false,
    val inkPageCount: Int = 1 // 手写页面数量，默认为1
)