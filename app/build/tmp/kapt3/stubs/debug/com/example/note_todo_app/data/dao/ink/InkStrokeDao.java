package com.example.note_todo_app.data.dao.ink;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\r\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00140\u00132\u0006\u0010\f\u001a\u00020\tH\'J$\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00140\u00132\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\'J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0018\u001a\u00020\u00032\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u001c"}, d2 = {"Lcom/example/note_todo_app/data/dao/ink/InkStrokeDao;", "", "deleteInkStroke", "", "inkStroke", "Lcom/example/note_todo_app/data/model/ink/InkStrokeEntity;", "(Lcom/example/note_todo_app/data/model/ink/InkStrokeEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteInkStrokeById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteInkStrokesByNoteId", "noteId", "deleteInkStrokesByNoteIdAndPage", "pageIndex", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getInkStrokeById", "getInkStrokesByNoteId", "Lkotlinx/coroutines/flow/Flow;", "", "getInkStrokesByNoteIdAndPage", "getPageIndicesForNote", "insertInkStroke", "insertInkStrokes", "inkStrokes", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInkStroke", "app_debug"})
@androidx.room.Dao()
public abstract interface InkStrokeDao {
    
    @androidx.room.Query(value = "SELECT * FROM ink_strokes WHERE note_id = :noteId ORDER BY page_index, created_at")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note_todo_app.data.model.ink.InkStrokeEntity>> getInkStrokesByNoteId(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId);
    
    @androidx.room.Query(value = "SELECT * FROM ink_strokes WHERE note_id = :noteId AND page_index = :pageIndex ORDER BY created_at")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.note_todo_app.data.model.ink.InkStrokeEntity>> getInkStrokesByNoteIdAndPage(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, int pageIndex);
    
    @androidx.room.Query(value = "SELECT * FROM ink_strokes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getInkStrokeById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.data.model.ink.InkStrokeEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertInkStroke(@org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.ink.InkStrokeEntity inkStroke, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertInkStrokes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.note_todo_app.data.model.ink.InkStrokeEntity> inkStrokes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateInkStroke(@org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.ink.InkStrokeEntity inkStroke, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteInkStroke(@org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.ink.InkStrokeEntity inkStroke, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM ink_strokes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteInkStrokeById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM ink_strokes WHERE note_id = :noteId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteInkStrokesByNoteId(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM ink_strokes WHERE note_id = :noteId AND page_index = :pageIndex")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteInkStrokesByNoteIdAndPage(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, int pageIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT page_index FROM ink_strokes WHERE note_id = :noteId ORDER BY page_index")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPageIndicesForNote(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Integer>> $completion);
}