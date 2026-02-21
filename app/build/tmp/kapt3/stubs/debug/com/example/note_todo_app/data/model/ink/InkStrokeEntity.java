package com.example.note_todo_app.data.model.ink;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\nH\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\rH\u00c6\u0003JY\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020\u0006H\u00d6\u0001J\t\u0010(\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010\u00a8\u0006)"}, d2 = {"Lcom/example/note_todo_app/data/model/ink/InkStrokeEntity;", "", "id", "", "noteId", "pageIndex", "", "strokeData", "color", "strokeWidth", "", "toolType", "createdAt", "Ljava/time/LocalDateTime;", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;FLjava/lang/String;Ljava/time/LocalDateTime;)V", "getColor", "()Ljava/lang/String;", "getCreatedAt", "()Ljava/time/LocalDateTime;", "getId", "getNoteId", "getPageIndex", "()I", "getStrokeData", "getStrokeWidth", "()F", "getToolType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "ink_strokes")
public final class InkStrokeEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @androidx.room.ColumnInfo(name = "note_id")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String noteId = null;
    @androidx.room.ColumnInfo(name = "page_index")
    private final int pageIndex = 0;
    @androidx.room.ColumnInfo(name = "stroke_data")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String strokeData = null;
    @androidx.room.ColumnInfo(name = "color")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String color = null;
    @androidx.room.ColumnInfo(name = "stroke_width")
    private final float strokeWidth = 0.0F;
    @androidx.room.ColumnInfo(name = "tool_type")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String toolType = null;
    @androidx.room.ColumnInfo(name = "created_at")
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDateTime createdAt = null;
    
    public InkStrokeEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String noteId, int pageIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String strokeData, @org.jetbrains.annotations.NotNull()
    java.lang.String color, float strokeWidth, @org.jetbrains.annotations.NotNull()
    java.lang.String toolType, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime createdAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNoteId() {
        return null;
    }
    
    public final int getPageIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStrokeData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getColor() {
        return null;
    }
    
    public final float getStrokeWidth() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToolType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDateTime component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.note_todo_app.data.model.ink.InkStrokeEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String noteId, int pageIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String strokeData, @org.jetbrains.annotations.NotNull()
    java.lang.String color, float strokeWidth, @org.jetbrains.annotations.NotNull()
    java.lang.String toolType, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime createdAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}