package steps

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kirabium.relayance.R
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.ui.addCustomer.AddCustomerActivity
import org.junit.Assert
import org.junit.Rule
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class AddCustomerSteps {

    // Rule to launch the AddCustomerActivity
    @get:Rule
    val activityRule = ActivityScenarioRule(AddCustomerActivity::class.java)

    private val repository = CustomerRepository()


    @Then("I should be redirected to the previous screen")
    fun iShouldBeRedirectedToThePreviousScreen() {
        // Verify that the current activity is not AddCustomerActivity
        activityRule.scenario.onActivity { activity ->
            Assert.assertTrue(activity.isFinishing)
        }
    }

    @Then("^the customer \"([^\"]*)\" should be added to the system$")
    fun theCustomerShouldBeAddedToTheSystem(customerName: String) {
        // Verify that the customer was added to the repository
        val customers = repository.getCustomers()
        val isCustomerAdded = customers.any { it.name == customerName }
        Assert.assertTrue("Customer $customerName was not added to the system", isCustomerAdded)

    }

    @Given("^I am on the \"([^\"]*)\" screen$")
    fun iAmOnTheScreen(arg0: String) {
        // Launch the AddCustomerActivity
        val intent = Intent(ApplicationProvider.getApplicationContext(), AddCustomerActivity::class.java)
        activityRule.scenario.onActivity { activity ->
            activity.startActivity(intent)
        }
    }

    @When("^I enter \"([^\"]*)\" in the \"([^\"]*)\" field$")
    fun iEnterInTheField(value: String, fieldName: String) {
        // Find the field by its ID and enter the value
        when (fieldName) {
            "Name" -> onView(withId(R.id.nameEditText)).perform(typeText(value), closeSoftKeyboard())
            "Email" -> onView(withId(R.id.emailEditText)).perform(typeText(value), closeSoftKeyboard())
            else -> throw IllegalArgumentException("Unknown field: $fieldName")
        }
    }

    @And("^I click the \"([^\"]*)\" button$")
    fun iClickTheButton(buttonName: String) {
        // Find the button by its ID and click it
        when (buttonName) {
            "Save" -> onView(withId(R.id.saveFab)).perform(click())
            else -> throw IllegalArgumentException("Unknown button: $buttonName")
        }
    }
}