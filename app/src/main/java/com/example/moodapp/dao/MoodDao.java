package com.example.moodapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moodapp.entities.Mood;

import java.util.List;

@Dao
public interface MoodDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Mood> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Mood mood);

    @Delete
    void deleteNote(Mood mood);
}
