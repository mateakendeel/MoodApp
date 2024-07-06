package com.example.moodapp.listeners;

import com.example.moodapp.entities.Note;

public interface MoodsListener {
    void onClickedNote(Note note, int position);

}
