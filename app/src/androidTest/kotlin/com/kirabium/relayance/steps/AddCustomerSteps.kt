package com.kirabium.relayance.steps

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kirabium.relayance.ui.addCustomer.AddCustomerActivity
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Rule

class AddCustomerSteps {

    @get:Rule
    val activityRule = ActivityScenarioRule(AddCustomerActivity::class.java)

    @Given("I am on the {string} screen")
    fun iAmOnTheScreen(screenName: String) {
        Espresso.onView(ViewMatchers.withText(screenName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @When("I enter {string} in the {string} field")
    fun iEnterInTheField(input: String, fieldName: String) {
        Espresso.onView(ViewMatchers.withHint(fieldName))
            .perform(ViewActions.typeText(input))
    }

    @When("I click the {string} button")
    fun iClickTheButton(buttonText: String) {
        Espresso.onView(ViewMatchers.withText(buttonText))
            .perform(ViewActions.click())
    }

    @Then("the customer {string} should be added to the system")
    fun theCustomerShouldBeAddedToTheSystem(customerName: String) {
        Espresso.onView(ViewMatchers.withText(customerName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Then("I should be redirected to the previous screen")
    fun iShouldBeRedirectedToThePreviousScreen() {
        Espresso.onView(ViewMatchers.withText("Add Customer"))
            .check(ViewAssertions.doesNotExist())
    }
}