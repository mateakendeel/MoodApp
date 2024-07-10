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

        Espresso.onView(ViewMatchers.withId(R.id.imageAddNoteMain))
                .perform(ViewActions.click());


        String testTitle = "Test Mood Title";
        String testSubtitle = "Test Subtitle";
        String testContent = "Test content";

        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .perform(ViewActions.typeText(testTitle));
        Espresso.onView(ViewMatchers.withId(R.id.inputSubtitle))
                .perform(ViewActions.typeText(testSubtitle));
        Espresso.onView(ViewMatchers.withId(R.id.inputNoteText))
                .perform(ViewActions.typeText(testContent));


        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Espresso.onView(ViewMatchers.withId(R.id.notesRecyclerView))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(testTitle))));


        Espresso.onView(ViewMatchers.withText(testTitle))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .check(ViewAssertions.matches(ViewMatchers.withText(testTitle)));


        Espresso.onView(ViewMatchers.withId(R.id.textChangeColor))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.layoutChangeColor))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.viewColor2))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Espresso.onView(ViewMatchers.withText(testTitle))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .check(ViewAssertions.matches(ViewMatchers.withText(testTitle)));

        Espresso.onView(ViewMatchers.withId(R.id.textChangeColor))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.layoutDeleteMood))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.textDeleteMood))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.notesRecyclerView))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.hasDescendant(ViewMatchers.withText(testTitle)))));
    }
}
