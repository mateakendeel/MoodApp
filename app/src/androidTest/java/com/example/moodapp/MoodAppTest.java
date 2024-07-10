package com.example.moodapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodapp.activities.CreateMoodActivity;
import com.example.moodapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MoodAppTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateAndDeleteNote() {
        // Click on the 'Add Note' button in MainActivity
        Espresso.onView(ViewMatchers.withId(R.id.imageAddNoteMain))
                .perform(ViewActions.click());

        // Enter title, subtitle, and text
        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .perform(ViewActions.typeText("Mood1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.inputSubtitle))
                .perform(ViewActions.typeText("Happiness"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.inputNoteText))
                .perform(ViewActions.typeText("I feel happy"), ViewActions.closeSoftKeyboard());

        // Change mood/color
        Espresso.onView(ViewMatchers.withId(R.id.layoutChangeColor))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.viewColor2)) // Selecting color 2 (you can adjust this based on your layout)
                .perform(ViewActions.click());

        // Save the note
        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());

        // Wait for the save operation (if needed)
        // Add a delay or synchronization mechanism if required

        // Confirm that the note is created by checking the RecyclerView (if applicable)

        // Click on the newly created note to view/update it
        Espresso.onView(ViewMatchers.withText("Mood1"))
                .perform(ViewActions.click());

        // Perform actions on the view/update screen if necessary

        // Delete the note
        Espresso.onView(ViewMatchers.withId(R.id.layoutDeleteMood))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Delete Mood"))
                .perform(ViewActions.click());

        // Wait for the delete operation (if needed)
        // Add a delay or synchronization mechanism if required
    }
}
