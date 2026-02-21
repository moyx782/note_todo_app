package com.example.note_todo_app.utils;

/**
 * Utility class for converting stroke data to ML Kit's Ink format
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tJ&\u0010\n\u001a\u00020\u00042\u001e\u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\t0\f0\u0006J\u001a\u0010\r\u001a\u00020\u00042\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0006J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/example/note_todo_app/utils/InkConversionUtils;", "", "()V", "convertFromInkStrokeEntity", "Lcom/google/mlkit/vision/digitalink/recognition/Ink;", "strokePoints", "", "", "timestamp", "", "convertMultipleStrokesToInk", "allStrokes", "Lkotlin/Pair;", "convertToDigitalInk", "strokeData", "downloadLanguageModel", "", "languageTag", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class InkConversionUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.note_todo_app.utils.InkConversionUtils INSTANCE = null;
    
    private InkConversionUtils() {
        super();
    }
    
    /**
     * Converts a list of stroke points to ML Kit's Ink format
     *
     * @param strokeData A list of lists of Offset points representing strokes
     * @return ML Kit's Ink object ready for recognition
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.mlkit.vision.digitalink.recognition.Ink convertToDigitalInk(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.util.List<float[]>> strokeData) {
        return null;
    }
    
    /**
     * Alternative method that converts from our InkStrokeEntity representation
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.mlkit.vision.digitalink.recognition.Ink convertFromInkStrokeEntity(@org.jetbrains.annotations.NotNull()
    java.util.List<float[]> strokePoints, long timestamp) {
        return null;
    }
    
    /**
     * Convert multiple strokes from InkStrokeEntity objects to a single Ink object
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.mlkit.vision.digitalink.recognition.Ink convertMultipleStrokesToInk(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends kotlin.Pair<? extends java.util.List<float[]>, java.lang.Long>> allStrokes) {
        return null;
    }
    
    /**
     * Download the specified language model for digital ink recognition
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object downloadLanguageModel(@org.jetbrains.annotations.NotNull()
    java.lang.String languageTag, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}