package com.example.note_todo_app.data.dao.ink

import androidx.room.*
import com.example.note_todo_app.data.model.ink.InkStrokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InkStrokeDao {
    @Query("SELECT * FROM ink_strokes WHERE note_id = :noteId ORDER BY page_index, created_at")
    fun getInkStrokesByNoteId(noteId: String): Flow<List<InkStrokeEntity>>

    @Query("SELECT * FROM ink_strokes WHERE note_id = :noteId AND page_index = :pageIndex ORDER BY created_at")
    fun getInkStrokesByNoteIdAndPage(noteId: String, pageIndex: Int): Flow<List<InkStrokeEntity>>

    @Query("SELECT * FROM ink_strokes WHERE id = :id")
    suspend fun getInkStrokeById(id: String): InkStrokeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInkStroke(inkStroke: InkStrokeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInkStrokes(inkStrokes: List<InkStrokeEntity>)

    @Update
    suspend fun updateInkStroke(inkStroke: InkStrokeEntity)

    @Delete
    suspend fun deleteInkStroke(inkStroke: InkStrokeEntity)

    @Query("DELETE FROM ink_strokes WHERE id = :id")
    suspend fun deleteInkStrokeById(id: String)

    @Query("DELETE FROM ink_strokes WHERE note_id = :noteId")
    suspend fun deleteInkStrokesByNoteId(noteId: String)

    @Query("DELETE FROM ink_strokes WHERE note_id = :noteId AND page_index = :pageIndex")
    suspend fun deleteInkStrokesByNoteIdAndPage(noteId: String, pageIndex: Int)

    @Query("SELECT DISTINCT page_index FROM ink_strokes WHERE note_id = :noteId ORDER BY page_index")
    suspend fun getPageIndicesForNote(noteId: String): List<Int>
}