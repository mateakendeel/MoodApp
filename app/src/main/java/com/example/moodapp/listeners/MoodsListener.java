package com.example.moodapp.listeners;

import com.example.moodapp.entities.Mood;

public interface MoodsListener {
    void onClickedNote(Mood mood, int position);

}
