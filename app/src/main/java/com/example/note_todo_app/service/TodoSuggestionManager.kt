package com.example.note_todo_app.service

import com.example.note_todo_app.data.model.Todo
import java.util.*

/**
 * Manager class that orchestrates todo suggestion functionality using both local rules and remote API
 */
class TodoSuggestionManager {
    
    private val todoSuggester = TodoSuggester()
    private val nlpPlanner = NlpPlanner()
    private var remoteApiEnabled = false
    
    /**
     * Generates todo suggestions from text content using local rule-based approach (MVP version)
     */
    fun generateSuggestionsFromText(text: String): List<Todo> {
        // First, try to extract todos using pattern matching
        val candidateTodos = todoSuggester.extractCandidateTodos(text)
        
        // Then apply de-duplication and merging strategy
        val mergedTodos = todoSuggester.deduplicateAndMerge(candidateTodos)
        
        // Also try to parse natural language for additional todos
        val nlpGeneratedTodos = nlpPlanner.parseNaturalLanguage(text)
        
        // Combine both sets of todos and merge again to avoid duplicates
        val allTodos = mergedTodos + nlpGeneratedTodos
        
        return todoSuggester.deduplicateAndMerge(allTodos)
    }
    
    /**
     * Adds a new todo to the suggestion list with basic processing
     */
    fun addNewTodo(title: String, description: String = "", noteId: String? = null): Todo {
        return Todo(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            isCompleted = false,
            priority = 0,
            dueDate = null,
            createdAt = java.util.Date(),
            updatedAt = java.util.Date(),
            noteId = noteId ?: ""
        )
    }
    
    /**
     * Enables/disables remote API usage for todo suggestions
     */
    fun setRemoteApiUsage(enabled: Boolean) {
        this.remoteApiEnabled = enabled
    }
    
    /**
     * Gets suggestions using either local or remote implementation based on settings
     */
    suspend fun getSuggestionsWithRemoteFallback(text: String): List<Todo> {
        if (remoteApiEnabled) {
            // TODO: Implement actual remote API call here
            // For MVP, we'll fall back to local implementation
            println("Remote API not yet implemented - falling back to local processing")
        }
        
        return generateSuggestionsFromText(text)
    }
    
    /**
     * Updates a todo's properties
     */
    fun updateTodo(
        todoId: String,
        title: String? = null,
        description: String? = null,
        isCompleted: Boolean? = null,
        priority: Int? = null,
        dueDate: String? = null
    ): Todo? {
        // In MVP, we just create an updated copy since we don't have persistence here
        // Actual persistence would be handled by the repository layer
        return null
    }
}