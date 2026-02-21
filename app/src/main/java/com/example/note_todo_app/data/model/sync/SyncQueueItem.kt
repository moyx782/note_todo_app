package com.example.note_todo_app.data.model.sync

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing an item in the synchronization queue (outbox pattern)
 */
@Entity(tableName = "sync_queue")
data class SyncQueueItem(
    @PrimaryKey
    val id: String,
    val operation: String, // CREATE, UPDATE, DELETE
    val entityType: String, // NOTE, TODO, INK_STROKE
    val entityId: String,
    val payload: String, // JSON string of the entity data
    val timestamp: Long,
    val version: Int = 1,
    val status: String = "PENDING", // PENDING, PROCESSED, FAILED
    val retryCount: Int = 0,
    val error: String? = null,
    val processedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
