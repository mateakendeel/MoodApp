package com.example.moodapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodapp.activities.CreateMoodActivity;
import com.example.moodapp.activities.MainActivity;
import com.example.moodapp.database.Database;
import com.example.moodapp.entities.Note;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CreateMoodActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateViewDeleteNote() {
        // Click on the add note button in MainActivity
        Espresso.onView(ViewMatchers.withId(R.id.imageAddNoteMain))
                .perform(ViewActions.click());

        // Fill in the note details (title, subtitle, content)
        String testTitle = "Test Mood Title";
        String testSubtitle = "Test Subtitle";
        String testContent = "Test note content";

        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .perform(ViewActions.typeText(testTitle));
        Espresso.onView(ViewMatchers.withId(R.id.inputSubtitle))
                .perform(ViewActions.typeText(testSubtitle));
        Espresso.onView(ViewMatchers.withId(R.id.inputNoteText))
                .perform(ViewActions.typeText(testContent));

        // Save the note
        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());

        // Wait for a short period to let the note save
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the note is saved by checking if it exists in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.notesRecyclerView))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(testTitle))));

        // Click on the saved note to view it
        Espresso.onView(ViewMatchers.withText(testTitle))
                .perform(ViewActions.click());

        // Verify that we are in the View/Update mode by checking if the title is displayed
        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .check(ViewAssertions.matches(ViewMatchers.withText(testTitle)));

        // Click on change color button and select a color
        Espresso.onView(ViewMatchers.withId(R.id.textChangeColor))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.layoutChangeColor))
                .perform(ViewActions.click()); // Expand color picker
        Espresso.onView(ViewMatchers.withId(R.id.viewColor2)) // Select a color (example: #2196F3)
                .perform(ViewActions.click());

        // Save the updated note
        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());

        // Wait for a short period to let the note save
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the saved note again to view it
        Espresso.onView(ViewMatchers.withText(testTitle))
                .perform(ViewActions.click());

        // Verify that we are still in the View/Update mode
        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .check(ViewAssertions.matches(ViewMatchers.withText(testTitle)));

        Espresso.onView(ViewMatchers.withId(R.id.textChangeColor))
                .perform(ViewActions.click());

        // Click on delete note option
        Espresso.onView(ViewMatchers.withId(R.id.layoutDeleteMood))
                .perform(ViewActions.click());

        // Confirm delete in dialog
        Espresso.onView(ViewMatchers.withId(R.id.textDeleteMood))
                .perform(ViewActions.click());


        // Verify that the note is deleted by checking if it no longer exists in the RecyclerView
        Espresso.onView(ViewMatchers.withId(R.id.notesRecyclerView))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.hasDescendant(ViewMatchers.withText(testTitle)))));
    }
}
