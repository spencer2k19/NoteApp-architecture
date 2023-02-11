package com.example.noteapp.feature_note.domaine.use_case

import com.example.noteapp.feature_note.domaine.model.Note
import com.example.noteapp.feature_note.domaine.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int):Note? {
        return repository.getNoteById(id)
    }
}