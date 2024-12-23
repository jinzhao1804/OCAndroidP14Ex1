package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Calendar

class generateDateTest {

    @Test
    fun `generateDate should return a date months back from today`() {
        // Arrange
        val monthsBack = 3
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -monthsBack)
        val expectedDate = calendar.time

        // Act
        val generatedDate = DummyData.generateDate(monthsBack)

        // Assert
        assertEquals(expectedDate.year, generatedDate.year)
        assertEquals(expectedDate.month, generatedDate.month)
        assertEquals(expectedDate.date, generatedDate.date)
    }
}