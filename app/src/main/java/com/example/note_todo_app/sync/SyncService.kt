package com.example.note_todo_app.sync

import com.example.note_todo_app.sync.dto.*

/**
 * Interface for synchronization operations
 */
interface SyncService {
    
    /**
     * Performs a sync operation - pulling changes from server and pushing local changes
     */
    suspend fun sync(): SyncResult
    
    /**
     * Queues a change to be synced later (used for outbox pattern)
     */
    suspend fun queueChange(change: ChangeDto): Boolean
    
    /**
     * Pushes all pending changes in the outbox to the server
     */
    suspend fun pushOutbox(): PushResult?
    
    /**
     * Pulls new changes from the server using the provided cursor
     */
    suspend fun pull(cursor: String?): SyncResponse?
    
    /**
     * Gets the current sync cursor (position of last sync)
     */
    suspend fun getCurrentCursor(): String?
    
    /**
     * Sets the current sync cursor
     */
    suspend fun setCurrentCursor(cursor: String)
    
    /**
     * Resolves conflicts according to the specified strategy
     */
    suspend fun resolveConflicts(conflicts: List<ConflictDto>): Boolean
}

/**
 * Result of a sync operation
 */
data class SyncResult(
    val success: Boolean,
    val pulledChangesCount: Int = 0,
    val pushedChangesCount: Int = 0,
    val conflicts: List<ConflictDto> = emptyList(),
    val newCursor: String? = null,
    val errorMessage: String? = null
)

/**
 * Result of a push operation
 */
data class PushResult(
    val success: Boolean,
    val appliedChanges: List<String>,
    val conflicts: List<ConflictDto>,
    val newCursor: String? = null,
    val errorMessage: String? = null
)