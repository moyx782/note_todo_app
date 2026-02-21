package com.example.note_todo_app.service

import com.example.note_todo_app.data.model.Note
import com.example.note_todo_app.api.*

/**
 * Service class that implements local content organization using rule-based approaches
 */
class LocalOrganizerService {
    
    /**
     * Organizes a note by generating summary, extracting lists, and suggesting tags
     */
    fun organizeNote(note: Note): OrganizationResult {
        val summary = generateSummary(note.content)
        val extractedLists = extractLists(note.content)
        val suggestedTags = suggestTags(note.content, note.title)
        
        return OrganizationResult(
            originalNoteId = note.id,
            summary = summary,
            extractedLists = extractedLists,
            suggestedTags = suggestedTags
        )
    }
    
    /**
     * Generates a summary of the given text
     */
    fun generateSummary(text: String): String {
        if (text.isBlank()) return ""
        
        // If text is short enough, return as-is
        if (text.length <= 100) return text
        
        // Split into sentences and take first few meaningful sentences
        val sentenceDelimiters = listOf('.', '!', '?', '。', '！', '？')
        var sentenceCount = 0
        var lastBreakIndex = 0
        
        for ((index, char) in text.withIndex()) {
            if (char in sentenceDelimiters) {
                sentenceCount++
                lastBreakIndex = index + 1
                
                // Limit to about 100 characters or 3 sentences
                if (lastBreakIndex >= 100 || sentenceCount >= 3) {
                    break
                }
            }
        }
        
        // If we couldn't find good sentence boundaries, just take first 100 chars
        val result = if (sentenceCount > 0) {
            text.substring(0, lastBreakIndex).trim()
        } else {
            text.take(100)
        }
        
        return if (result.endsWith('.') || result.endsWith('。')) result else "$result…"
    }
    
    /**
     * Extracts list-like items from the text
     */
    fun extractLists(text: String): List<String> {
        val lines = text.lines()
        val listItems = mutableListOf<String>()
        
        // Patterns for different types of list items
        val listItemPatterns = listOf(
            Regex("""^\d+\.\s+(.*)"""),           // Numbered lists: "1. Item", "2. Another item"
            Regex("""^[•●○▪]\s+(.*)"""),          // Bullet points: "• Item", "● Another item"  
            Regex("""^-\s+(.*)"""),               // Dash lists: "- Item"
            Regex("""^\*\s+(.*)"""),              // Asterisk lists: "* Item"
            Regex("""^\[\s*[✗✓✓✗]\s*\]\s+(.*)""")  // Checkbox lists: "[ ] Item", "[x] Completed"
        )
        
        for (line in lines) {
            for (pattern in listItemPatterns) {
                val match = pattern.find(line.trim())
                if (match != null) {
                    listItems.add(match.groupValues[1].trim())
                    break // Only add once even if multiple patterns match
                }
            }
        }
        
        return listItems
    }
    
    /**
     * Suggests relevant tags based on content and title
     */
    fun suggestTags(content: String, title: String): List<String> {
        // Combine title and content for better tag extraction
        val combinedText = "$title $content".lowercase()
        
        // Remove common stop words but preserve domain-specific terms
        val stopWords = setOf(
            "the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", 
            "of", "with", "by", "is", "are", "was", "were", "be", "been", "being", 
            "have", "has", "had", "do", "does", "did", "will", "would", "could", 
            "should", "i", "you", "he", "she", "it", "we", "they", "me", "him", 
            "her", "us", "them", "my", "your", "his", "its", "our", "their"
        )
        
        // Extract potential keywords
        val wordPattern = Regex("[a-zA-Z一-龥]+") // Latin letters and Chinese characters
        val allMatches = wordPattern.findAll(combinedText)
        val words = allMatches.map { it.value }.filter { 
            it.length > 2 && it !in stopWords 
        }
        
        // Count frequency of each word
        val wordFreq = mutableMapOf<String, Int>()
        for (word in words) {
            wordFreq[word] = wordFreq.getOrDefault(word, 0) + 1
        }
        
        // Sort by frequency and length (prefer longer, more specific terms)
        val sortedWords = wordFreq.entries
            .sortedWith(compareByDescending<Map.Entry<String, Int>> { it.value }
                                   .thenByDescending { it.key.length })
        
        // Return top 8 unique tags
        return sortedWords.take(8).map { it.key }.distinct()
    }
    
    /**
     * Extracts todo items from text using pattern matching
     */
    fun extractTodosFromText(text: String): TodoExtractionResponse {
        val todos = mutableListOf<TodoItem>()
        val lines = text.lines()
        
        // Pattern for todo-like statements
        val todoPatterns = listOf(
            Regex("""(?:需要|要|计划)\s*(?:完成|做|处理|准备).*?(?=[:：,\n]|$)"""),  // "需要完成...", "要做..."
            Regex("""(?:提醒|记得|别忘了)\s*.+?(?=[:：,\n]|$)"""),                 // "提醒做...", "记得..."
            Regex("""明天|后天|下周|下个月\s*.*?(?:开会|提交|完成|买|去|办理).*?(?=[:：,\n]|$)"""), // Time + action
            Regex("""截止到?\s*\S{0,10}\s*(?:年|月|日)?\s*.*?(?:提交|完成|交付|上报).*?(?=[:：,\n]|$)""") // Deadline + task
        )
        
        for (line in lines) {
            for (pattern in todoPatterns) {
                val matches = pattern.findAll(line)
                for (match in matches) {
                    val todoText = match.value.trim()
                    
                    // Determine priority based on urgency indicators
                    val priority = when {
                        todoText.contains("紧急") || todoText.contains("马上") || todoText.contains("立刻") -> 2 // High
                        todoText.contains("尽快") || todoText.contains("今天") || todoText.contains("立即") -> 1 // Medium
                        else -> 0 // Low
                    }
                    
                    todos.add(TodoItem(
                        title = cleanTodoTitle(todoText),
                        priority = priority
                    ))
                }
            }
        }
        
        return TodoExtractionResponse(todos.distinctBy { it.title }, todos.size)
    }
    
    /**
     * Cleans up a todo title by removing prefixes and normalizing
     */
    private fun cleanTodoTitle(title: String): String {
        var cleaned = title
        
        // Remove common prefixes
        val prefixes = listOf("需要", "要", "计划", "提醒", "记得", "别忘了", "截止到?", "请")
        for (prefix in prefixes) {
            if (cleaned.startsWith(prefix)) {
                cleaned = cleaned.substring(prefix.length).trim()
                break
            }
        }
        
        // Clean up any remaining punctuation
        cleaned = cleaned.trim(':', '：', ',', '，', '.', '。').trim()
        
        return cleaned
    }
}
