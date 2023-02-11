package com.example.noteapp.feature_note.data.repository

import com.example.noteapp.feature_note.data.data_source.NoteDao
import com.example.noteapp.feature_note.domaine.model.Note
import com.example.noteapp.feature_note.domaine.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao:NoteDao
):NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun addNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}