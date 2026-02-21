package com.example.note_todo_app.data.dao

import androidx.room.*
import com.example.note_todo_app.data.model.sync.SyncQueueItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the synchronization queue (outbox pattern)
 */
@Dao
interface SyncQueueDao {
    
    /**
     * Inserts a new sync item into the queue
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncItem(item: SyncQueueItem): Long
    
    /**
     * Inserts multiple sync items into the queue
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncItems(items: List<SyncQueueItem>)
    
    /**
     * Updates an existing sync item in the queue
     */
    @Update
    suspend fun updateSyncItem(item: SyncQueueItem)
    
    /**
     * Deletes a sync item from the queue
     */
    @Delete
    suspend fun deleteSyncItem(item: SyncQueueItem)
    
    /**
     * Deletes a sync item by its ID
     */
    @Query("DELETE FROM sync_queue WHERE id = :id")
    suspend fun deleteSyncItemById(id: String)
    
    /**
     * Gets all pending sync items from the queue
     */
    @Query("SELECT * FROM sync_queue WHERE status = 'PENDING' ORDER BY createdAt ASC")
    fun getAllPendingSyncItems(): Flow<List<SyncQueueItem>>
    
    /**
     * Gets all sync items with a specific entity type
     */
    @Query("SELECT * FROM sync_queue WHERE entityType = :entityType AND status = 'PENDING'")
    fun getPendingSyncItemsByEntityType(entityType: String): Flow<List<SyncQueueItem>>
    
    /**
     * Gets all sync items that have failed previously
     */
    @Query("SELECT * FROM sync_queue WHERE status = 'FAILED'")
    fun getFailedSyncItems(): Flow<List<SyncQueueItem>>
    
    /**
     * Marks a sync item as processed
     */
    @Query("UPDATE sync_queue SET status = 'PROCESSED', processedAt = :processedAt WHERE id = :id")
    suspend fun markAsProcessed(id: String, processedAt: Long)
    
    /**
     * Marks a sync item as failed
     */
    @Query("UPDATE sync_queue SET status = 'FAILED', error = :error, retryCount = retryCount + 1 WHERE id = :id")
    suspend fun markAsFailed(id: String, error: String?)
    
    /**
     * Resets failed items to pending state for retry
     */
    @Query("UPDATE sync_queue SET status = 'PENDING' WHERE status = 'FAILED' AND retryCount < 3")
    suspend fun resetFailedItemsForRetry()
    
    /**
     * Clears all processed items older than the specified time
     */
    @Query("DELETE FROM sync_queue WHERE status = 'PROCESSED' AND processedAt < :olderThan")
    suspend fun clearOldProcessedItems(olderThan: Long)
}