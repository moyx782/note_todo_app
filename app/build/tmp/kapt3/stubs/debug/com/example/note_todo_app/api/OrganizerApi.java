package com.example.note_todo_app.api;

/**
 * Interface for the Organizer API that handles content organization tasks
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/example/note_todo_app/api/OrganizerApi;", "", "extractTodos", "Lcom/example/note_todo_app/api/TodoExtractionResponse;", "request", "Lcom/example/note_todo_app/api/TextProcessingRequest;", "(Lcom/example/note_todo_app/api/TextProcessingRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "organizeNote", "Lcom/example/note_todo_app/api/OrganizationResult;", "note", "Lcom/example/note_todo_app/data/model/Note;", "(Lcom/example/note_todo_app/data/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suggestTags", "Lcom/example/note_todo_app/api/TagSuggestionResponse;", "summarizeText", "Lcom/example/note_todo_app/api/SummaryResponse;", "app_debug"})
public abstract interface OrganizerApi {
    
    /**
     * Organize note content by extracting summary, creating lists, and suggesting tags
     */
    @retrofit2.http.POST(value = "/organize/note")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object organizeNote(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.api.OrganizationResult> $completion);
    
    /**
     * Extract a summary from the given text
     */
    @retrofit2.http.POST(value = "/organize/summarize")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object summarizeText(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.api.TextProcessingRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.api.SummaryResponse> $completion);
    
    /**
     * Extract action items/todo suggestions from the given text
     */
    @retrofit2.http.POST(value = "/organize/extract-todos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object extractTodos(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.api.TextProcessingRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.api.TodoExtractionResponse> $completion);
    
    /**
     * Suggest relevant tags for the given text
     */
    @retrofit2.http.POST(value = "/organize/suggest-tags")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object suggestTags(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.note_todo_app.api.TextProcessingRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.note_todo_app.api.TagSuggestionResponse> $completion);
}