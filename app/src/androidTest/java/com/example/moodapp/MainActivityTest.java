package com.example.moodapp;
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
public class MainActivityTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testAddNewNote() {

        Espresso.onView(ViewMatchers.withId(R.id.imageAddNotMain))
                .perform(ViewActions.click());


        Espresso.onView(ViewMatchers.withId(R.id.inputTittleName))
                .perform(ViewActions.typeText("Mood1"));
        Espresso.onView(ViewMatchers.withId(R.id.inputSubtitle))
                .perform(ViewActions.typeText("Emotion"));
        Espresso.onView(ViewMatchers.withId(R.id.inputNote))
                .perform(ViewActions.typeText("I feel happy"));


        Espresso.onView(ViewMatchers.withId(R.id.imageSaveNote))
                .perform(ViewActions.click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
