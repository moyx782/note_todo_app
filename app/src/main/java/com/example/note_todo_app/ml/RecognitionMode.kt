package com.example.note_todo_app.ml

import android.util.Log
import com.example.note_todo_app.data.model.ink.InkStrokeEntity
import com.google.mlkit.vision.digitalink.recognition.Ink

/**
 * Enum to define different recognition modes
 */
enum class RecognitionMode {
    /**
     * Full text recognition - recognized text becomes part of the note's content
     */
    FULL_TEXT_RECOGNITION,
    
    /**
     * Index-only recognition - recognized text is stored separately for search/indexing purposes
     */
    INDEX_ONLY_RECOGNITION
}

/**
 * Extended recognition engine that supports both full-text and index-only recognition modes
 */
class DualModeRecognitionEngine {
    
    private val digitalInkRecognizer = DigitalInkRecognizer()
    private var isInitialized = false
    
    companion object {
        private const val TAG = "DualModeRecognitionEngine"
    }
    
    /**
     * Initialize the recognition engine with a specific language model
     */
    suspend fun initialize(languageTag: String = "zh-CN"): Boolean {
        return try {
            isInitialized = digitalInkRecognizer.initialize(languageTag)
            Log.d(TAG, "Dual-mode recognition engine initialized with language: $languageTag")
            isInitialized
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize dual-mode recognition engine", e)
            false
        }
    }
    
    /**
     * Perform recognition based on the specified mode
     */
    suspend fun recognize(
        ink: Ink, 
        mode: RecognitionMode
    ): RecognitionResult {
        if (!isInitialized) {
            Log.w(TAG, "Recognition engine not initialized. Call initialize() first.")
            return RecognitionResult.Failure("Recognition engine not initialized")
        }
        
        return try {
            // Perform the actual recognition
            val candidates = digitalInkRecognizer.recognize(ink)
            
            if (candidates.isNullOrEmpty()) {
                RecognitionResult.Empty
            } else {
                when (mode) {
                    RecognitionMode.FULL_TEXT_RECOGNITION -> {
                        // In full text mode, we want to incorporate the recognized text into the note content
                        RecognitionResult.Success(candidates, addToIndex = false)
                    }
                    RecognitionMode.INDEX_ONLY_RECOGNITION -> {
                        // In index-only mode, we only store the text for search purposes
                        RecognitionResult.Success(candidates, addToIndex = true)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error during recognition in mode $mode", e)
            RecognitionResult.Failure(e.message ?: "Unknown error during recognition")
        }
    }
    
    /**
     * Convenience method to recognize strokes with a specific mode
     */
    suspend fun recognizeStrokes(
        strokes: List<InkStrokeEntity>, 
        mode: RecognitionMode
    ): RecognitionResult {
        // Convert strokes to Ink format (implementation would depend on how stroke data is stored)
        // This is a simplified version - actual implementation would need to properly deserialize
        // the stroke data from InkStrokeEntity objects
        
        // For now, returning empty result as the conversion logic depends on the actual storage format
        return if (strokes.isEmpty()) {
            RecognitionResult.Empty
        } else {
            // Placeholder - would need proper conversion from InkStrokeEntity to Ink
            RecognitionResult.Failure("Stroke conversion not implemented yet")
        }
    }
    
    /**
     * Process raw stroke data through the entire recognition pipeline with mode selection
     */
    suspend fun processRawStrokeData(
        strokePoints: List<List<FloatArray>>,
        mode: RecognitionMode
    ): RecognitionResult {
        if (!isInitialized) {
            Log.w(TAG, "Recognition engine not initialized. Call initialize() first.")
            return RecognitionResult.Failure("Recognition engine not initialized")
        }
        
        return try {
            // Convert to ML Kit's Ink format using our utility
            val ink = com.example.note_todo_app.utils.InkConversionUtils.convertToDigitalInk(strokePoints)
            
            // Perform recognition with the specified mode
            recognize(ink, mode)
        } catch (e: Exception) {
            Log.e(TAG, "Error processing raw stroke data in mode $mode", e)
            RecognitionResult.Failure(e.message ?: "Unknown error during recognition")
        }
    }
    
    /**
     * Check if the required models are downloaded and available
     */
    suspend fun isModelAvailable(): Boolean {
        if (!isInitialized) {
            Log.w(TAG, "Recognition engine not initialized. Call initialize() first.")
            return false
        }
        
        return digitalInkRecognizer.isModelDownloaded()
    }
    
    /**
     * Download the required models if they're not already available
     */
    suspend fun ensureModelDownloaded(): Boolean {
        if (!isModelAvailable()) {
            return digitalInkRecognizer.downloadModel()
        }
        return true
    }
    
    /**
     * Close resources when done
     */
    fun close() {
        digitalInkRecognizer.close()
        isInitialized = false
    }
}
