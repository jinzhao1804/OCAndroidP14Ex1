package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.util.Calendar

class isNewCustomerTest {

    @Test
    fun `isNewCustomer should return true if the customer was created within the last 3 months`() {
        // Arrange
        val newCustomerDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -1) // Set date to 1 month ago
        }.time
        val customer = Customer(1, "Test User", "test@example.com", newCustomerDate)

        // Act
        val result = customer.isNewCustomer()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isNewCustomer should return false if the customer was created more than 3 months ago`() {
        // Arrange
        val oldCustomerDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -4) // Set date to 4 months ago
        }.time
        val customer = Customer(2, "Old User", "old@example.com", oldCustomerDate)

        // Act
        val result = customer.isNewCustomer()

        // Assert
        assertFalse(result)
    }
}