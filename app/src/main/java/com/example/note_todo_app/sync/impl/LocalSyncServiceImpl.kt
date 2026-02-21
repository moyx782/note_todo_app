package com.example.note_todo_app.sync.impl

import com.example.note_todo_app.data.dao.NoteDao
import com.example.note_todo_app.data.dao.TodoDao
import com.example.note_todo_app.data.dao.ink.InkStrokeDao
import com.example.note_todo_app.data.dao.SyncQueueDao
import com.example.note_todo_app.data.model.sync.SyncQueueItem
import com.example.note_todo_app.data.store.SettingsDataStore
import com.example.note_todo_app.sync.SyncService
import com.example.note_todo_app.sync.SyncResult
import com.example.note_todo_app.sync.PushResult
import com.example.note_todo_app.sync.dto.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Local implementation of SyncService that manages synchronization logic
 */
class LocalSyncServiceImpl(
    private val noteDao: NoteDao,
    private val todoDao: TodoDao,
    private val inkStrokeDao: InkStrokeDao,
    private val syncQueueDao: SyncQueueDao,
    private val settingsDataStore: SettingsDataStore
) : SyncService {
    
    companion object {
        const val SYNC_CURSOR_KEY = "sync_cursor"
        const val LAST_SYNC_TIME_KEY = "last_sync_time"
    }
    
    override suspend fun sync(): SyncResult {
        try {
            // Get the last sync cursor
            val lastCursor = getCurrentCursor()
            
            // Pull changes from server
            val pullResponse = pull(lastCursor)
            if (pullResponse != null) {
                // Apply pulled changes to local database
                applyPulledChanges(pullResponse)
                
                // Update cursor to the new position
                setCurrentCursor(pullResponse.cursor)
            }
            
            // Push local changes to server
            val pushResult = pushOutbox()
            
            return SyncResult(
                success = true,
                pulledChangesCount = pullResponse?.let { 
                    (it.notes?.size ?: 0) + (it.todos?.size ?: 0) + (it.inkStrokes?.size ?: 0)
                } ?: 0,
                pushedChangesCount = pushResult?.appliedChanges?.size ?: 0,
                conflicts = pushResult?.conflicts ?: emptyList(),
                newCursor = pullResponse?.cursor,
                errorMessage = null
            )
        } catch (e: Exception) {
            return SyncResult(
                success = false,
                errorMessage = e.message
            )
        }
    }
    
    override suspend fun queueChange(change: ChangeDto): Boolean {
        try {
            // Serialize the change payload to JSON string
            val payloadJson = Json.encodeToString<Map<String, Any>>(change.payload as Map<String, Any>)
            
            // Create a sync queue item
            val syncItem = SyncQueueItem(
                id = change.id,
                operation = change.operation.name,
                entityType = change.entityType.name,
                entityId = change.id, // Using change ID as entity ID for simplicity
                payload = payloadJson,
                timestamp = change.timestamp,
                version = change.version
            )
            
            // Insert into the sync queue
            syncQueueDao.insertSyncItem(syncItem)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    
    override suspend fun pushOutbox(): PushResult? {
        try {
            // Fetch all pending changes from the outbox table
            val pendingItems = syncQueueDao.getAllPendingSyncItems().first()
            
            if (pendingItems.isEmpty()) {
                return PushResult(
                    success = true,
                    appliedChanges = emptyList(),
                    conflicts = emptyList(),
                    newCursor = System.currentTimeMillis().toString(),
                    errorMessage = null
                )
            }
            
            // In a real implementation, this would:
            // 1. Send the pending changes to the server via API call
            // 2. Process the response and handle any conflicts
            // 3. Remove successfully applied changes from the outbox
            
            // For simulation purposes, let's mark all items as processed
            for (item in pendingItems) {
                syncQueueDao.markAsProcessed(item.id, System.currentTimeMillis())
            }
            
            return PushResult(
                success = true,
                appliedChanges = pendingItems.map { it.id },
                conflicts = emptyList(),
                newCursor = System.currentTimeMillis().toString(),
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return PushResult(
                success = false,
                appliedChanges = emptyList(),
                conflicts = emptyList(),
                errorMessage = e.message
            )
        }
    }
    
    override suspend fun pull(cursor: String?): SyncResponse? {
        // In a real implementation, this would make an API call to fetch changes since the cursor
        // For simulation, returning a sample response with recent local data
        
        // Get local data that has changed since the cursor timestamp (if provided)
        val notes = if (cursor == null) {
            noteDao.getAllNotes()
        } else {
            // Parse cursor as timestamp and get notes updated after that time
            val cursorTime = cursor.toLongOrNull() ?: 0L
            noteDao.getNotesUpdatedSince(cursorTime)
        }
        
        val todos = if (cursor == null) {
            todoDao.getAllTodos()
        } else {
            val cursorTime = cursor.toLongOrNull() ?: 0L
            todoDao.getTodosUpdatedSince(cursorTime)
        }
        
        val inkStrokes = if (cursor == null) {
            inkStrokeDao.getAllInkStrokes()
        } else {
            val cursorTime = cursor.toLongOrNull() ?: 0L
            inkStrokeDao.getInkStrokesUpdatedSince(cursorTime)
        }
        
        return SyncResponse(
            cursor = System.currentTimeMillis().toString(), // New cursor is current time
            notes = notes,
            todos = todos,
            inkStrokes = inkStrokes.map { stroke ->
                InkStrokeDto(
                    id = stroke.id,
                    noteId = stroke.noteId,
                    pageNumber = stroke.pageIndex,
                    strokesData = stroke.strokesData,
                    createdAt = stroke.createdAt,
                    updatedAt = stroke.updatedAt,
                    version = stroke.version
                )
            },
            deletedItems = emptyList(), // Would come from a deletion tracking table in real impl
            hasMore = false
        )
    }
    
    override suspend fun getCurrentCursor(): String? {
        return settingsDataStore.getStringValue(SYNC_CURSOR_KEY).first()
    }
    
    override suspend fun setCurrentCursor(cursor: String) {
        settingsDataStore.putStringValue(SYNC_CURSOR_KEY, cursor)
    }
    
    override suspend fun resolveConflicts(conflicts: List<ConflictDto>): Boolean {
        // In a real implementation, this would implement conflict resolution strategies
        // based on the ResolutionStrategy specified for each conflict
        for (conflict in conflicts) {
            when (conflict.resolutionStrategy) {
                ResolutionStrategy.CLIENT_WINS -> {
                    // Keep client version - no action needed as it's already in our DB
                    // May need to send this back to server depending on architecture
                }
                ResolutionStrategy.SERVER_WINS -> {
                    // Apply server version to local DB
                    // This would involve updating the local record with server data
                }
                ResolutionStrategy.MERGE -> {
                    // Attempt to merge changes intelligently
                    // Implementation depends on the specific entity type and fields
                }
                ResolutionStrategy.CUSTOM_HANDLER -> {
                    // Use custom conflict resolution handler
                    // Could involve user interaction or domain-specific logic
                }
                null -> {
                    // Default behavior - could be configured per app setting
                    // For now, defaulting to server wins
                }
            }
        }
        
        return true
    }
    
    /**
     * Applies pulled changes to the local database
     */
    private suspend fun applyPulledChanges(response: SyncResponse) {
        // Apply note changes
        response.notes?.forEach { note ->
            // Check if note exists locally
            val existingNote = noteDao.getNoteById(note.id)
            
            if (existingNote != null) {
                // Note exists, update it
                noteDao.updateNote(note)
            } else {
                // Note doesn't exist, insert it
                noteDao.insertNote(note)
            }
        }
        
        // Apply todo changes
        response.todos?.forEach { todo ->
            val existingTodo = todoDao.getTodoById(todo.id)
            
            if (existingTodo != null) {
                // Todo exists, update it
                todoDao.updateTodo(todo)
            } else {
                // Todo doesn't exist, insert it
                todoDao.insertTodo(todo)
            }
        }
        
        // Apply ink stroke changes
        response.inkStrokes?.forEach { strokeDto ->
            // Convert DTO back to entity
            val strokeEntity = com.example.note_todo_app.data.model.ink.InkStrokeEntity(
                id = strokeDto.id,
                noteId = strokeDto.noteId,
                pageIndex = strokeDto.pageNumber, // Changed from pageNumber to pageIndex to match actual model
                strokesData = strokeDto.strokesData,
                createdAt = strokeDto.createdAt,
                updatedAt = strokeDto.updatedAt,
                version = strokeDto.version
            )
            
            // Insert or update in local DB
            inkStrokeDao.insertInkStroke(strokeEntity)
        }
        
        // Handle deletions
        response.deletedItems?.forEach { deletedItem ->
            when (deletedItem.type) {
                ItemType.NOTE -> noteDao.deleteNoteById(deletedItem.id)
                ItemType.TODO -> todoDao.deleteTodoById(deletedItem.id)
                ItemType.INK_STROKE -> inkStrokeDao.deleteInkStrokeById(deletedItem.id)
            }
        }
    }
}