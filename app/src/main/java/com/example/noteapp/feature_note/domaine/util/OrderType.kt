package com.example.noteapp.feature_note.domaine.util

sealed class OrderType{
    object Ascending:OrderType()
    object Descending:OrderType()
}
