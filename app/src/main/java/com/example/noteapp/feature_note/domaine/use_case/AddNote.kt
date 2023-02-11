package com.example.noteapp.feature_note.domaine.use_case

import com.example.noteapp.feature_note.domaine.model.InvalidNoteException
import com.example.noteapp.feature_note.domaine.model.Note
import com.example.noteapp.feature_note.domaine.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
)  {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw  InvalidNoteException("The title of the note can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty")
        }
        repository.addNote(note)
    }
}