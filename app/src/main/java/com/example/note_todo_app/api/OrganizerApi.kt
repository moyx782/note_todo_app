package com.example.note_todo_app.api

import com.example.note_todo_app.data.model.Note
import retrofit2.http.*

/**
 * Interface for the Organizer API that handles content organization tasks
 */
interface OrganizerApi {
    
    /**
     * Organize note content by extracting summary, creating lists, and suggesting tags
     */
    @POST("/organize/note")
    suspend fun organizeNote(@Body note: Note): OrganizationResult
    
    /**
     * Extract a summary from the given text
     */
    @POST("/organize/summarize")
    suspend fun summarizeText(@Body request: TextProcessingRequest): SummaryResponse
    
    /**
     * Extract action items/todo suggestions from the given text
     */
    @POST("/organize/extract-todos")
    suspend fun extractTodos(@Body request: TextProcessingRequest): TodoExtractionResponse
    
    /**
     * Suggest relevant tags for the given text
     */
    @POST("/organize/suggest-tags")
    suspend fun suggestTags(@Body request: TextProcessingRequest): TagSuggestionResponse
}

/**
 * Request object for text processing operations
 */
data class TextProcessingRequest(
    val text: String,
    val language: String = "zh-CN", // Default to Chinese
    val options: Map<String, Any> = emptyMap()
)

/**
 * Response for organization operations
 */
data class OrganizationResult(
    val originalNoteId: String,
    val summary: String? = null,
    val extractedLists: List<String> = emptyList(),
    val suggestedTags: List<String> = emptyList(),
    val processedContent: String? = null
)

/**
 * Response for summarization operations
 */
data class SummaryResponse(
    val inputTextLength: Int,
    val summary: String,
    val confidence: Float = 0.0f
)

/**
 * Response for todo extraction operations
 */
data class TodoExtractionResponse(
    val todos: List<TodoItem>,
    val count: Int
)

/**
 * Represents a single todo item extracted from text
 */
data class TodoItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val priority: Int = 0, // 0: low, 1: medium, 2: high
    val dueDate: String? = null, // ISO 8601 format date string
    val estimatedTimeMinutes: Int? = null
)

/**
 * Response for tag suggestion operations
 */
data class TagSuggestionResponse(
    val tags: List<TagSuggestion>,
    val count: Int
)

/**
 * Represents a suggested tag with relevance score
 */
data class TagSuggestion(
    val name: String,
    val relevanceScore: Float = 1.0f // Higher is more relevant
)


// Mock implementation for local usage
class LocalOrganizerService {
    
    /**
     * Local implementation of note organization using rule-based approach
     */
    fun organizeNoteLocally(note: Note): OrganizationResult {
        val summary = generateSummary(note.content)
        val extractedLists = extractLists(note.content)
        val suggestedTags = suggestTagsLocally(note.content, note.title)
        
        return OrganizationResult(
            originalNoteId = note.id,
            summary = summary,
            extractedLists = extractedLists,
            suggestedTags = suggestedTags
        )
    }
    
    private fun generateSummary(content: String): String {
        // Simple rule-based summarization
        if (content.length <= 100) return content
        
        // Take first sentence or first 100 chars ending at sentence boundary
        val sentences = content.split(Regex("[.!?。！？]"))
        return if (sentences.isNotEmpty()) {
            "${sentences[0].trim()}${if(sentences[0].trim().lastOrNull()?.toString() in listOf(".", "!", "?", "。", "！", "？")) "" else "."}"
        } else {
            content.take(100)
        }
    }
    
    private fun extractLists(content: String): List<String> {
        // Find lines that look like list items
        val listPattern = Regex("\\d+\\.\\s.*|•\\s.*|-\\s.*|\\*\\s.*")
        val matches = listPattern.findAll(content)
        return matches.map { it.value.trim() }.toList()
    }
    
    private fun suggestTagsLocally(content: String, title: String): List<String> {
        // Basic keyword extraction for tag suggestions
        val combinedText = "$title $content".lowercase()
        val commonWords = setOf("the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with", "by", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "do", "does", "did", "will", "would", "could", "should")
        
        val words = combinedText.replace(Regex("[^a-zA-Z一-龥\\s]"), " ")
            .split("\\s+".toRegex())
            .filter { it.length > 3 && it !in commonWords }
            .distinct()
        
        // Return top 5 most frequent significant words as tags
        return words.take(5)
    }
}
