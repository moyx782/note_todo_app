package com.example.note_todo_app.sync

import com.example.note_todo_app.sync.dto.*

/**
 * Mock implementation of sync API for testing purposes
 */
class MockSyncApi {
    
    // In-memory storage to simulate server-side data
    private val serverNotes = mutableMapOf<String, Map<String, Any>>()
    private val serverTodos = mutableMapOf<String, Map<String, Any>>()
    private val serverInkStrokes = mutableMapOf<String, Map<String, Any>>()
    private var lastServerTimestamp = System.currentTimeMillis()
    
    /**
     * Simulates pulling changes from the server
     */
    suspend fun pull(syncRequest: SyncRequest): SyncResponse {
        // Simulate network delay
        kotlinx.coroutines.delay(500)
        
        val notes = if (syncRequest.includeNotes) {
            filterByLastCursor(serverNotes, syncRequest.lastCursor)
        } else null
        
        val todos = if (syncRequest.includeTodos) {
            filterByLastCursor(serverTodos, syncRequest.lastCursor)
        } else null
        
        val inkStrokes = if (syncRequest.includeInkStrokes) {
            filterByLastCursor(serverInkStrokes, syncRequest.lastCursor)
        } else null
        
        // Update timestamp for next sync
        lastServerTimestamp = System.currentTimeMillis()
        
        return SyncResponse(
            cursor = lastServerTimestamp.toString(),
            notes = notes?.map { /* Convert map back to Note objects */ emptyList() } ?: emptyList(),
            todos = todos?.map { /* Convert map back to Todo objects */ emptyList() } ?: emptyList(),
            inkStrokes = inkStrokes?.map { 
                InkStrokeDto(
                    id = it["id"] as? String ?: "",
                    noteId = it["noteId"] as? String ?: "",
                    pageNumber = it["pageNumber"] as? Int ?: 0,
                    strokesData = it["strokesData"] as? String ?: "",
                    createdAt = it["createdAt"] as? Long ?: 0L,
                    updatedAt = it["updatedAt"] as? Long ?: 0L,
                    version = it["version"] as? Int ?: 1
                )
            } ?: emptyList(),
            deletedItems = emptyList(), // For simplicity, not implementing deletions in mock
            hasMore = false
        )
    }
    
    /**
     * Simulates pushing changes to the server
     */
    suspend fun push(pushRequest: PushRequest): PushResponse {
        // Simulate network delay
        kotlinx.coroutines.delay(500)
        
        val appliedChanges = mutableListOf<String>()
        val conflicts = mutableListOf<ConflictDto>()
        
        for (change in pushRequest.changes) {
            try {
                // Simulate potential conflict detection
                val existingOnServer = when (change.entityType) {
                    EntityType.NOTE -> serverNotes[change.id]
                    EntityType.TODO -> serverTodos[change.id]
                    EntityType.INK_STROKE -> serverInkStrokes[change.id]
                }
                
                if (existingOnServer != null && change.version > 1) {
                    // Simulate a conflict if there's already an item and this is not the first version
                    conflicts.add(ConflictDto(
                        entityId = change.id,
                        entityType = change.entityType,
                        serverVersion = existingOnServer["version"] as? Int ?: 1,
                        clientVersion = change.version,
                        conflictType = com.example.note_todo_app.sync.dto.ConflictType.MODIFIED_ON_BOTH_SIDES,
                        resolutionStrategy = null
                    ))
                } else {
                    // Apply the change to the simulated server
                    when (change.entityType) {
                        EntityType.NOTE -> {
                            if (change.operation == OperationType.DELETE) {
                                serverNotes.remove(change.id)
                            } else {
                                serverNotes[change.id] = change.payload
                            }
                        }
                        EntityType.TODO -> {
                            if (change.operation == OperationType.DELETE) {
                                serverTodos.remove(change.id)
                            } else {
                                serverTodos[change.id] = change.payload
                            }
                        }
                        EntityType.INK_STROKE -> {
                            if (change.operation == OperationType.DELETE) {
                                serverInkStrokes.remove(change.id)
                            } else {
                                serverInkStrokes[change.id] = change.payload
                            }
                        }
                    }
                    appliedChanges.add(change.id)
                }
            } catch (e: Exception) {
                // Log error but continue processing other changes
                e.printStackTrace()
            }
        }
        
        return PushResponse(
            success = true,
            appliedChanges = appliedChanges,
            conflicts = conflicts,
            newCursor = System.currentTimeMillis().toString()
        )
    }
    
    /**
     * Helper function to filter items based on the last cursor/timestamp
     */
    private fun filterByLastCursor(
        sourceMap: Map<String, Map<String, Any>>, 
        lastCursor: String?
    ): List<Map<String, Any>> {
        val cursorTime = lastCursor?.toLongOrNull() ?: 0L
        return sourceMap.values.filter { item ->
            val updatedAt = item["updatedAt"] as? Long ?: item["createdAt"] as? Long ?: 0L
            updatedAt >= cursorTime
        }
    }
    
    /**
     * Adds initial test data to the mock server
     */
    fun addTestData() {
        // Add some sample notes
        serverNotes["note-1"] = mapOf(
            "id" to "note-1",
            "title" to "Sample Note 1",
            "content" to "This is a sample note for testing sync functionality.",
            "createdAt" to System.currentTimeMillis() - 3600000, // 1 hour ago
            "updatedAt" to System.currentTimeMillis() - 3600000,
            "tags" to listOf("test", "sample"),
            "isPinned" to false,
            "inkPageCount" to 0
        )
        
        serverNotes["note-2"] = mapOf(
            "id" to "note-2",
            "title" to "Sample Note 2",
            "content" to "Another sample note with different content.",
            "createdAt" to System.currentTimeMillis() - 1800000, // 30 minutes ago
            "updatedAt" to System.currentTimeMillis() - 1800000,
            "tags" to listOf("important"),
            "isPinned" to true,
            "inkPageCount" to 2
        )
        
        // Add some sample todos
        serverTodos["todo-1"] = mapOf(
            "id" to "todo-1",
            "title" to "Complete project setup",
            "description" to "Finish setting up the Android project structure",
            "isCompleted" to false,
            "priority" to 2,
            "dueDate" to null,
            "createdAt" to System.currentTimeMillis() - 7200000, // 2 hours ago
            "updatedAt" to System.currentTimeMillis() - 7200000,
            "noteId" to "note-1"
        )
        
        serverTodos["todo-2"] = mapOf(
            "id" to "todo-2",
            "title" to "Implement sync feature",
            "description" to "Create the synchronization framework",
            "isCompleted" to true,
            "priority" to 1,
            "dueDate" to "2023-12-31",
            "createdAt" to System.currentTimeMillis() - 3600000, // 1 hour ago
            "updatedAt" to System.currentTimeMillis() - 1800000, // Updated 30 mins ago
            "noteId" to "note-2"
        )
    }
}