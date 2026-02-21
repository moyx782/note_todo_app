package com.example.note_todo_app.ml;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J*\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00102\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00100\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/note_todo_app/ml/DigitalInkRecognizer;", "", "()V", "languageTag", "", "recognizer", "Lcom/google/mlkit/vision/digitalink/recognition/DigitalInkRecognizer;", "close", "", "downloadModel", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initialize", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isModelDownloaded", "recognize", "", "ink", "Lcom/google/mlkit/vision/digitalink/recognition/Ink;", "(Lcom/google/mlkit/vision/digitalink/recognition/Ink;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recognizeStrokes", "strokeData", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class DigitalInkRecognizer {
    @org.jetbrains.annotations.Nullable()
    private com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognizer recognizer;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String languageTag = "zh-CN";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DigitalInkRecognizer";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.note_todo_app.ml.DigitalInkRecognizer.Companion Companion = null;
    
    public DigitalInkRecognizer() {
        super();
    }
    
    /**
     * Initialize the digital ink recognizer with the specified language
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object initialize(@org.jetbrains.annotations.NotNull()
    java.lang.String languageTag, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Recognize the provided ink data
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recognize(@org.jetbrains.annotations.NotNull()
    com.google.mlkit.vision.digitalink.recognition.Ink ink, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Convenience method to convert stroke data and recognize it in one call
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recognizeStrokes(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.util.List<float[]>> strokeData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Check if the model is available locally
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object isModelDownloaded(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Download the recognition model for the current language
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object downloadModel(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Close the recognizer and free resources
     */
    public final void close() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/note_todo_app/ml/DigitalInkRecognizer$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}