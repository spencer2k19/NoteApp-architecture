package com.example.noteapp.feature_note.domaine.repository

import com.example.noteapp.feature_note.domaine.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes():Flow<List<Note>>

    suspend fun getNoteById(id:Int):Note?

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)
}