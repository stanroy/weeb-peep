package com.stanroy.weebpeep.presentation.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.stanroy.weebpeep.MainActivity
import com.stanroy.weebpeep.R
import org.junit.Rule
import org.junit.Test

class UpcomingAnimeFragmentTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_recyclerView_isVisible() {
        onView(withId(R.id.rv_anime_list)).check(matches(isDisplayed()))
    }
}
