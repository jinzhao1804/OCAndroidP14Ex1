package com.kirabium.relayance

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.activity.DetailActivity
import org.junit.Rule
import org.junit.Test

class CustomerDetailsAreCorrectTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<DetailActivity>()

    @Test
    fun testCustomerDetailsAreCorrect() {
        val customerId = 1
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customerId)
        }

        ActivityScenario.launch<DetailActivity>(intent).use { scenario ->
            val customer = DummyData.customers.find { it.id == customerId }
                ?: throw AssertionError("Customer with ID $customerId not found in DummyData")

            // Debug the expected text
            val createdAtText = "Created at: ${customer.createdAt.toHumanDate()}"
            // Verify name, email, and creation date are displayed
            composeTestRule.onNodeWithText(customer.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(customer.email).assertIsDisplayed()
            composeTestRule.onNodeWithText(createdAtText).assertIsDisplayed()

            // Verify "new" ribbon if applicable
            if (customer.isNewCustomer()) {
                composeTestRule.onNodeWithText("NEW").assertIsDisplayed()
            }
        }
    }


}
