package com.example.moodapp; // Add the package statement here

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moodapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MediumTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateAndSaveNewNote() {
        Espresso.onView(ViewMatchers.withId(R.id.imageAddNotMain))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .perform(ViewActions.typeText("Mood2"));
        Espresso.onView(ViewMatchers.withId(R.id.inputSubtitle))
                .perform(ViewActions.typeText("Emotion2"));
        Espresso.onView(ViewMatchers.withId(R.id.inputNote))
                .perform(ViewActions.typeText("I feel sad."));


        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testViewAndExitSavedNote() {

        Espresso.onView(ViewMatchers.withId(R.id.notesRecyclerView))
                .perform(ViewActions.click());


        try {
            Espresso.pressBack();
        } catch (Exception e) {
        }
    }

}
