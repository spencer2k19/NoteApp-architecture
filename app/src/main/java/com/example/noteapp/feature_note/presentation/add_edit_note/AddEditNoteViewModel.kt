package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domaine.model.InvalidNoteException
import com.example.noteapp.feature_note.domaine.model.Note
import com.example.noteapp.feature_note.domaine.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel() {
     private val _noteTitle = mutableStateOf(NoteTextFieldState(
         hint = "Enter title..."
     ))
    val noteTitle:State<NoteTextFieldState> = _noteTitle


    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent:State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor:State<Int> = _noteColor

     private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId:Int? = null


    init {
        savedStateHandle.get<Int>("noteId")?.let {noteId->
            if (noteId != -1) {
                viewModelScope.launch {
                  notesUseCases.getNote(noteId)?.also {note->
                      currentNoteId = note.id
                      _noteTitle.value = noteTitle.value.copy(
                          text = note.title,
                          isHintVisible = false
                      )
                      _noteContent.value = noteContent.value.copy(
                          text = note.content,
                          isHintVisible = false
                      )

                      _noteColor.value = note.color
                  }

                }

            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }


            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNote(Note(
                            title = noteTitle.value.text,
                            content = noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = noteColor.value,
                            id = currentNoteId
                        ))

                        _eventFlow.emit(UIEvent.SaveNote)
                    }catch (e:InvalidNoteException) {
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            message = e.message?:"Couldn' save note"
                        ))
                    }
                }
            }
        }
    }




    sealed class  UIEvent {
        data class ShowSnackbar(val message:String):UIEvent()
        object SaveNote:UIEvent()
    }

}