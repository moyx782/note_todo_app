package com.example.note_todo_app.sync.dto

import com.example.note_todo_app.data.model.Note
import com.example.note_todo_app.data.model.Todo

/**
 * Data Transfer Objects for synchronization operations
 */

// Base sync request/response objects
data class SyncRequest(
    val clientId: String,
    val lastCursor: String?,
    val includeNotes: Boolean = true,
    val includeTodos: Boolean = true,
    val includeInkStrokes: Boolean = true
)

data class SyncResponse(
    val cursor: String,
    val notes: List<Note>? = null,
    val todos: List<Todo>? = null,
    val inkStrokes: List<InkStrokeDto>? = null,
    val deletedItems: List<DeletedItemDto>? = null,
    val hasMore: Boolean = false
)

// Ink stroke data transfer object
data class InkStrokeDto(
    val id: String,
    val noteId: String,
    val pageNumber: Int,
    val strokesData: String, // Serialized strokes data
    val createdAt: Long,
    val updatedAt: Long,
    val version: Int = 1
)

// Deleted items tracking
data class DeletedItemDto(
    val id: String,
    val type: ItemType,
    val deletedAt: Long
)

enum class ItemType {
    NOTE, TODO, INK_STROKE
}

// Push-specific objects
data class PushRequest(
    val clientId: String,
    val changes: List<ChangeDto>,
    val timestamp: Long = System.currentTimeMillis()
)

data class PushResponse(
    val success: Boolean,
    val appliedChanges: List<String>, // IDs of successfully applied changes
    val conflicts: List<ConflictDto>,
    val newCursor: String? = null
)

// Change representation for sync operations
data class ChangeDto(
    val id: String,
    val operation: OperationType,
    val entityType: EntityType,
    val payload: Map<String, Any>, // Serialized entity data
    val timestamp: Long,
    val version: Int = 1
)

enum class OperationType {
    CREATE, UPDATE, DELETE
}

enum class EntityType {
    NOTE, TODO, INK_STROKE
}

// Conflict resolution objects
data class ConflictDto(
    val entityId: String,
    val entityType: EntityType,
    val serverVersion: Int,
    val clientVersion: Int,
    val conflictType: ConflictType,
    val resolutionStrategy: ResolutionStrategy?
)

enum class ConflictType {
    MODIFIED_ON_BOTH_SIDES,
    DELETED_ON_SERVER_MODIFIED_ON_CLIENT,
    MODIFIED_ON_SERVER_DELETED_ON_CLIENT
}

enum class ResolutionStrategy {
    CLIENT_WINS,      // Use client version
    SERVER_WINS,      // Use server version
    MERGE,            // Attempt to merge changes
    CUSTOM_HANDLER   // Custom conflict resolution
}