package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domaine.model.Note
import com.example.noteapp.feature_note.domaine.util.NoteOrder
import com.example.noteapp.feature_note.domaine.util.OrderType

data class NotesState(
    val notes:List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending),
    val isOrderSectionVisible:Boolean = false

)