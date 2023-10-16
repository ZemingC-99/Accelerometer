package com.cs501.accelerometer

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule

import org.junit.After
import org.junit.Before

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun testThresholdExceeded() {
        // Set a threshold value that should trigger a toast message
        val thresholdValue = 10
        val toastMessage = "Significant movement detected on X-axis!"

        // Find the SeekBar and set its progress
        Espresso.onView(ViewMatchers.withId(R.id.thresholdSeekBar))
            .perform(ViewActions.click()) // Click on the SeekBar to focus it
            .perform(ViewActions.swipeLeft())

        // Verify that the threshold value is displayed in the TextView
        Espresso.onView(ViewMatchers.withId(R.id.thresholdValueTextView))
            .check(ViewAssertions.matches(ViewMatchers.withText("Current Threshold: $thresholdValue")))

        // Simulate significant movement on X-axis (greater than the threshold), should trigger a toast message
        Espresso.onView(ViewMatchers.isRoot())
            .perform(ViewActions.click())

        // Verify that the expected toast message happens
        Espresso.onView(ViewMatchers.withText(toastMessage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun tearDown() {
    }
}