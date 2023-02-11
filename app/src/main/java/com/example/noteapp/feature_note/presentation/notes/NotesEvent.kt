package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domaine.model.Note
import com.example.noteapp.feature_note.domaine.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder):NotesEvent()
     data class DeleteNote(val note: Note):NotesEvent()
    object RestoreNote:NotesEvent()
    object ToggleOrderSection:NotesEvent()
}
