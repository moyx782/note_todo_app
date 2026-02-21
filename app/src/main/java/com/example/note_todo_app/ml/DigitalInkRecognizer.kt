package com.example.note_todo_app.ml

import android.util.Log
import com.example.note_todo_app.utils.InkConversionUtils
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognizer as MlKitDigitalInkRecognizer
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognizerOptions
import com.google.mlkit.vision.digitalink.recognition.Ink
import kotlinx.coroutines.tasks.await

class DigitalInkRecognizer {
    private var recognizer: MlKitDigitalInkRecognizer? = null
    private var languageTag: String = "zh-CN" // Default to Chinese, can be changed
    
    companion object {
        private const val TAG = "DigitalInkRecognizer"
    }
    
    /**
     * Initialize the digital ink recognizer with the specified language
     */
    suspend fun initialize(languageTag: String = "zh-CN"): Boolean {
        return try {
            this.languageTag = languageTag
            val modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag(languageTag)
                ?: return false
            val model = DigitalInkRecognitionModel.builder(modelIdentifier).build()
            recognizer = DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model).build()
            )
            Log.d(TAG, "Digital ink recognizer initialized for language: $languageTag")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize digital ink recognizer", e)
            false
        }
    }
    
    /**
     * Recognize the provided ink data
     */
    suspend fun recognize(ink: Ink): List<String>? {
        if (recognizer == null) {
            Log.w(TAG, "Recognizer not initialized. Call initialize() first.")
            return null
        }
        
        return try {
            val result = recognizer?.recognize(ink)?.await()
            result?.candidates?.map { candidate ->
                candidate.text
            } ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Recognition failed", e)
            null
        }
    }
    
    /**
     * Convenience method to convert stroke data and recognize it in one call
     */
    suspend fun recognizeStrokes(strokeData: List<List<FloatArray>>): List<String>? {
        val ink = InkConversionUtils.convertToDigitalInk(strokeData)
        return recognize(ink)
    }
    
    /**
     * Check if the model is available locally
     */
    suspend fun isModelDownloaded(): Boolean {
        return try {
            val modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag(languageTag)
                ?: return false
            val model = DigitalInkRecognitionModel.builder(modelIdentifier).build()
            val manager = RemoteModelManager.getInstance()
            manager.isModelDownloaded(model).await()
        } catch (e: Exception) {
            Log.e(TAG, "Error checking model download status", e)
            false
        }
    }
    
    /**
     * Download the recognition model for the current language
     */
    suspend fun downloadModel(): Boolean {
        return try {
            val modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag(languageTag)
                ?: return false
            val model = DigitalInkRecognitionModel.builder(modelIdentifier).build()
            val conditions = DownloadConditions.Builder()
                .requireWifi() // Consider removing this for better UX
                .build()
            
            val manager = RemoteModelManager.getInstance()
            manager.download(model, conditions).await()
            Log.d(TAG, "Successfully downloaded model for $languageTag")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to download model for $languageTag", e)
            false
        }
    }
    
    /**
     * Close the recognizer and free resources
     */
    fun close() {
        recognizer?.close()
        recognizer = null
    }
}
