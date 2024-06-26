package com.example.moodapp.listeners;

import com.example.moodapp.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);

}
