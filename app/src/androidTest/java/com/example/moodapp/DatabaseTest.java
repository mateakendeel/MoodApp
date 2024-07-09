package com.example.moodapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodapp.database.Database;
import com.example.moodapp.entities.Mood;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private Database database;

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, Database.class).build();
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    @Test
    public void testInsertNote() throws Exception {
        Mood mood = new Mood();
        mood.setTitle("Test Title");
        mood.setDateTime("2024-06-05 10:00:00");
        mood.setSubtitle("Test Subtitle");
        mood.setNoteText("Test Mood Text");

        database.MoodDao().insertNote(mood);
        Mood dbMood = database.MoodDao().getAllNotes().get(0);

        assertEquals(dbMood.getTitle(), "Test Title");
    }

    @Test
    public void testNoteDateTime() throws Exception {
        Mood mood = new Mood();
        mood.setTitle("Test Title");
        mood.setDateTime("2024-06-05 10:00:00");
        mood.setSubtitle("Test Subtitle");
        mood.setNoteText("Test Mood Text");

        database.MoodDao().insertNote(mood);
        Mood dbMood = database.MoodDao().getAllNotes().get(0);

        assertEquals(dbMood.getDateTime(), "2024-06-05 10:00:00");
    }

    @Test
    public void testNoteSubtitle() throws Exception {
        Mood mood = new Mood();
        mood.setTitle("Test Title");
        mood.setDateTime("2024-06-05 10:00:00");
        mood.setSubtitle("Test Subtitle");
        mood.setNoteText("Test Mood Text");

        database.MoodDao().insertNote(mood);
        Mood dbMood = database.MoodDao().getAllNotes().get(0);

        assertEquals(dbMood.getSubtitle(), "Test Subtitle");
    }

}
