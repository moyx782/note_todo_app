package com.example.note_todo_app.data.dao

import androidx.room.*
import com.example.note_todo_app.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY priority DESC, dueDate ASC, updatedAt DESC")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todos WHERE noteId = :noteId ORDER BY priority DESC, dueDate ASC, updatedAt DESC")
    fun getTodosByNoteId(noteId: String): Flow<List<Todo>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: String): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodoById(id: String)

    @Query("UPDATE todos SET isCompleted = :completed WHERE id = :id")
    suspend fun updateTodoCompletion(id: String, completed: Boolean)
}