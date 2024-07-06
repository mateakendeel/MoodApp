package com.example.moodapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodapp.database.Database;
import com.example.moodapp.entities.Note;

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
        Note note = new Note();
        note.setTitle("Test Title");
        note.setDateTime("2024-06-05 10:00:00");
        note.setSubtitle("Test Subtitle");
        note.setNoteText("Test Note Text");

        database.noteDao().insertNote(note);
        Note dbNote = database.noteDao().getAllNotes().get(0);

        assertEquals(dbNote.getTitle(), "Test Title");
    }

    @Test
    public void testNoteDateTime() throws Exception {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setDateTime("2024-06-05 10:00:00");
        note.setSubtitle("Test Subtitle");
        note.setNoteText("Test Note Text");

        database.noteDao().insertNote(note);
        Note dbNote = database.noteDao().getAllNotes().get(0);

        assertEquals(dbNote.getDateTime(), "2024-06-05 10:00:00");
    }

    @Test
    public void testNoteSubtitle() throws Exception {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setDateTime("2024-06-05 10:00:00");
        note.setSubtitle("Test Subtitle");
        note.setNoteText("Test Note Text");

        database.noteDao().insertNote(note);
        Note dbNote = database.noteDao().getAllNotes().get(0);

        assertEquals(dbNote.getSubtitle(), "Test Subtitle");
    }

}
