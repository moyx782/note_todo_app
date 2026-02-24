package com.example.note_todo_app;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001a2\u0010\u0002\u001a\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0003\u001a\u0016\u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0003\u001aX\u0010\t\u001a\u00020\u00012\b\u0010\n\u001a\u0004\u0018\u00010\u000b26\u0010\f\u001a2\u0012\u0013\u0012\u00110\u000e\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u000e\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0003\u001aZ\u0010\u0013\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00152\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00010\u00182\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00010\u0018H\u0003\u001a\u001e\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u000e2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0003\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0007\u001a`\u0010\u001d\u001a\u00020\u00012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00152\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u00182\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00010\u00182\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00010\u0018H\u0003\u00a8\u0006#"}, d2 = {"AppEntry", "", "HomePage", "onNotesClick", "Lkotlin/Function0;", "onTodosClick", "onInkClick", "InkPage", "onBack", "NoteEditorPage", "note", "Lcom/example/note_todo_app/UiNote;", "onSave", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "title", "content", "NotesListPage", "notes", "", "onAdd", "onOpenNote", "Lkotlin/Function1;", "", "onDeleteNote", "PlaceholderPage", "PreviewGreeting", "TodosPage", "todos", "Lcom/example/note_todo_app/UiTodo;", "onAddTodo", "onToggleDone", "onDeleteTodo", "app_debug"})
public final class MainActivityKt {
    
    @androidx.compose.runtime.Composable()
    private static final void AppEntry() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HomePage(kotlin.jvm.functions.Function0<kotlin.Unit> onNotesClick, kotlin.jvm.functions.Function0<kotlin.Unit> onTodosClick, kotlin.jvm.functions.Function0<kotlin.Unit> onInkClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NotesListPage(java.util.List<com.example.note_todo_app.UiNote> notes, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<kotlin.Unit> onAdd, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onOpenNote, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onDeleteNote) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TodosPage(java.util.List<com.example.note_todo_app.UiTodo> todos, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAddTodo, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onToggleDone, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onDeleteTodo) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InkPage(kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NoteEditorPage(com.example.note_todo_app.UiNote note, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlaceholderPage(java.lang.String title, kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PreviewGreeting() {
    }
}