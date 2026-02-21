package com.example.note_todo_app.ml;

/**
 * Extended recognition engine that supports both full-text and index-only recognition modes
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\nJ*\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u001bJ$\u0010\u001c\u001a\u00020\u00112\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/note_todo_app/ml/DualModeRecognitionEngine;", "", "()V", "digitalInkRecognizer", "Lcom/example/note_todo_app/ml/DigitalInkRecognizer;", "isInitialized", "", "close", "", "ensureModelDownloaded", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initialize", "languageTag", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isModelAvailable", "processRawStrokeData", "Lcom/example/note_todo_app/ml/RecognitionResult;", "strokePoints", "", "", "mode", "Lcom/example/note_todo_app/ml/RecognitionMode;", "(Ljava/util/List;Lcom/example/note_todo_app/ml/RecognitionMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recognize", "ink", "Lcom/google/mlkit/vision/digitalink/recognition/Ink;", "(Lcom/google/mlkit/vision/digitalink/recognition/Ink;Lcom/example/note_todo_app/ml/RecognitionMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recognizeStrokes", "strokes", "Lcom/example/note_todo_app/data/model/ink/InkStrokeEntity;", "Companion", "app_debug"})
public final class DualModeRecognitionEngine {
    @org.jetbrains.annotations.NotNull()
    private final com.example.note_todo_app.ml.DigitalInkRecognizer digitalInkRecognizer = null;
    private boolean isInitialized = false;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DualModeRecognitionEngine";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.note_todo_app.ml.DualModeRecognitionEngine.Companion Companion = null;
    
    public DualModeRecognitionEngine() {
        super();
    }
    
    /**
     * Initialize the recognition engine with a specific language model
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object initialize(@org.jetbrains.annotations.NotNull()
    java.lang.String languageTag, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Perform recognition based on the specified mode
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recognize(@org.jetbrains.annotations.NotNull()
    com.google.mlkit.vision.digitalink.recognition.Ink ink, @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.ml.RecognitionMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.ml.RecognitionResult> $completion) {
        return null;
    }
    
    /**
     * Convenience method to recognize strokes with a specific mode
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recognizeStrokes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.note_todo_app.data.model.ink.InkStrokeEntity> strokes, @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.ml.RecognitionMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.ml.RecognitionResult> $completion) {
        return null;
    }
    
    /**
     * Process raw stroke data through the entire recognition pipeline with mode selection
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processRawStrokeData(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.util.List<float[]>> strokePoints, @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.ml.RecognitionMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.ml.RecognitionResult> $completion) {
        return null;
    }
    
    /**
     * Check if the required models are downloaded and available
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object isModelAvailable(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Download the required models if they're not already available
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object ensureModelDownloaded(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Close resources when done
     */
    public final void close() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/note_todo_app/ml/DualModeRecognitionEngine$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}