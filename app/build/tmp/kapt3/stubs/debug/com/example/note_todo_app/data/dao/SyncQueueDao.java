package com.example.note_todo_app.data.dao;

/**
 * Data Access Object for the synchronization queue (outbox pattern)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0011\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u0010H\'J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u0010H\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u00102\u0006\u0010\u0014\u001a\u00020\rH\'J\u0016\u0010\u0015\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0016\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0018J \u0010\u0019\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u001e\u0010\u001c\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010 J\u0016\u0010!\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\""}, d2 = {"Lcom/example/note_todo_app/data/dao/SyncQueueDao;", "", "clearOldProcessedItems", "", "olderThan", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSyncItem", "item", "Lcom/example/note_todo_app/data/model/sync/SyncQueueItem;", "(Lcom/example/note_todo_app/data/model/sync/SyncQueueItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSyncItemById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPendingSyncItems", "Lkotlinx/coroutines/flow/Flow;", "", "getFailedSyncItems", "getPendingSyncItemsByEntityType", "entityType", "insertSyncItem", "insertSyncItems", "items", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsFailed", "error", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsProcessed", "processedAt", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetFailedItemsForRetry", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSyncItem", "app_debug"})
@androidx.room.Dao()
public abstract interface SyncQueueDao {
    
    /**
     * Inserts a new sync item into the queue
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSyncItem(@org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.sync.SyncQueueItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Inserts multiple sync items into the queue
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSyncItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.note_todo_app.data.model.sync.SyncQueueItem> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Updates an existing sync item in the queue
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSyncItem(@org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.sync.SyncQueueItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes a sync item from the queue
     */
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSyncItem(@org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.sync.SyncQueueItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Deletes a sync item by its ID
     */
    @androidx.room.Query(value = "DELETE FROM sync_queue WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSyncItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Gets all pending sync items from the queue
     */
    @androidx.room.Query(value = "SELECT * FROM sync_queue WHERE status = \'PENDING\' ORDER BY createdAt ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note_todo_app.data.model.sync.SyncQueueItem>> getAllPendingSyncItems();
    
    /**
     * Gets all sync items with a specific entity type
     */
    @androidx.room.Query(value = "SELECT * FROM sync_queue WHERE entityType = :entityType AND status = \'PENDING\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note_todo_app.data.model.sync.SyncQueueItem>> getPendingSyncItemsByEntityType(@org.jetbrains.annotations.NotNull()
    java.lang.String entityType);
    
    /**
     * Gets all sync items that have failed previously
     */
    @androidx.room.Query(value = "SELECT * FROM sync_queue WHERE status = \'FAILED\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note_todo_app.data.model.sync.SyncQueueItem>> getFailedSyncItems();
    
    /**
     * Marks a sync item as processed
     */
    @androidx.room.Query(value = "UPDATE sync_queue SET status = \'PROCESSED\', processedAt = :processedAt WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAsProcessed(@org.jetbrains.annotations.NotNull()
    java.lang.String id, long processedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Marks a sync item as failed
     */
    @androidx.room.Query(value = "UPDATE sync_queue SET status = \'FAILED\', error = :error, retryCount = retryCount + 1 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAsFailed(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Resets failed items to pending state for retry
     */
    @androidx.room.Query(value = "UPDATE sync_queue SET status = \'PENDING\' WHERE status = \'FAILED\' AND retryCount < 3")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object resetFailedItemsForRetry(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Clears all processed items older than the specified time
     */
    @androidx.room.Query(value = "DELETE FROM sync_queue WHERE status = \'PROCESSED\' AND processedAt < :olderThan")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearOldProcessedItems(long olderThan, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}