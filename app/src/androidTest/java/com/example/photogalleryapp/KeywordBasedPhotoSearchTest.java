package com.example.photogalleryapp;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class KeywordBasedPhotoSearchTest {

    @Rule
    public ActivityTestRule<GalleryActivity> rule
            = new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void Should_RetrieveMatchingPhotos_When_InquireByKeyword() {
        // Context of the app under test.
        onView(withId(R.id.button_searchPopup))
                .perform(click());
        onView(withId(R.id.text_keyword))
                .perform(typeText("dog"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm))
                .perform(click());
        // Pending functional implementation
        assertEquals("success", "fail");
    }
}

