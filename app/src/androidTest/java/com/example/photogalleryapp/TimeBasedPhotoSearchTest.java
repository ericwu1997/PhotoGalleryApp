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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TimeBasedPhotoSearchTest {

    @Rule
    public ActivityTestRule<GalleryActivity> rule
            = new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void Should_LaunchGalleryActivity_When_SearchButtonClicked() {
        // Context of the app under test.

    }
}

