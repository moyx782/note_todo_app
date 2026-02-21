package com.example.note_todo_app.utils

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.recognition.Ink
import kotlinx.coroutines.tasks.await
import java.util.*

/**
 * Utility class for converting stroke data to ML Kit's Ink format
 */
object InkConversionUtils {
    
    /**
     * Converts a list of stroke points to ML Kit's Ink format
     *
     * @param strokeData A list of lists of Offset points representing strokes
     * @return ML Kit's Ink object ready for recognition
     */
    fun convertToDigitalInk(strokeData: List<List<FloatArray>>): Ink {
        val builder = Ink.builder()
        
        for (stroke in strokeData) {
            val strokeBuilder = Ink.Stroke.builder()
            
            for ((index, point) in stroke.withIndex()) {
                if (point.size >= 2) { // Ensure x and y coordinates exist
                    val x = point[0]
                    val y = point[1]
                    
                    // Add timestamp - using index-based timestamps since we don't have real timing data
                    val time = (index * 10).toLong() // Assuming 10ms per point
                    
                    val inkPoint = Ink.Point.create(x, y, time)
                    strokeBuilder.addPoint(inkPoint)
                }
            }
            
            builder.addStroke(strokeBuilder.build())
        }
        
        return builder.build()
    }
    
    /**
     * Alternative method that converts from our InkStrokeEntity representation
     */
    fun convertFromInkStrokeEntity(
        strokePoints: List<FloatArray>, 
        timestamp: Long = System.currentTimeMillis()
    ): Ink {
        val strokeBuilder = Ink.Stroke.builder()
        
        for ((index, point) in strokePoints.withIndex()) {
            if (point.size >= 2) {
                val x = point[0]
                val y = point[1]
                
                // Calculate relative time based on position in stroke
                val time = timestamp + (index * 5) // Assume 5ms between points
                
                val inkPoint = Ink.Point.create(x, y, time)
                strokeBuilder.addPoint(inkPoint)
            }
        }
        
        val inkBuilder = Ink.builder()
        inkBuilder.addStroke(strokeBuilder.build())
        
        return inkBuilder.build()
    }
    
    /**
     * Convert multiple strokes from InkStrokeEntity objects to a single Ink object
     */
    fun convertMultipleStrokesToInk(
        allStrokes: List<Pair<List<FloatArray>, Long>>
        // Pair of (points, baseTimestamp) for each stroke
    ): Ink {
        val inkBuilder = Ink.builder()
        
        for ((strokePoints, baseTimestamp) in allStrokes) {
            val strokeBuilder = Ink.Stroke.builder()
            
            for ((index, point) in strokePoints.withIndex()) {
                if (point.size >= 2) {
                    val x = point[0]
                    val y = point[1]
                    
                    // Calculate relative time based on position in stroke
                    val time = baseTimestamp + (index * 5) // Assume 5ms between points
                    
                    val inkPoint = Ink.Point.create(x, y, time)
                    strokeBuilder.addPoint(inkPoint)
                }
            }
            
            inkBuilder.addStroke(strokeBuilder.build())
        }
        
        return inkBuilder.build()
    }
    
    /**
     * Download the specified language model for digital ink recognition
     */
    suspend fun downloadLanguageModel(languageTag: String): Boolean {
        return try {
            val modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag(languageTag)
                ?: return false
            val model = DigitalInkRecognitionModel.builder(modelIdentifier).build()
            val conditions = DownloadConditions.Builder()
                .requireWifi() // Consider removing this for better UX
                .build()
            
            RemoteModelManager.getInstance().download(model, conditions).await()
            Log.d("InkConversionUtils", "Successfully downloaded model for $languageTag")
            true
        } catch (e: Exception) {
            Log.e("InkConversionUtils", "Failed to download model for $languageTag", e)
            false
        }
    }
}
