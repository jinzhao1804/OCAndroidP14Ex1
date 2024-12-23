package com.kirabium.relayance

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kirabium.relayance.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test

class numberOfCustomersInList {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java) // Launches the activity


    @Test
    fun testNumberOfCustomersInList() {
        // Launch MainActivity
        onView(withId(R.id.customerRecyclerView)) // Assuming customerRecyclerView is the ID
            .check(matches(hasChildCount(5))) // Verifies that there are 5 items
    }
}