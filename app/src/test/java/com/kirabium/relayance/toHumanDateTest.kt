package com.kirabium.relayance

import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class toHumanDateTest {

    @Test
    fun toHumanDate_should_format_date_to_ddMMyyyy() {
        // Arrange
        val date = Calendar.getInstance().apply {
            set(2024, Calendar.DECEMBER, 23) // Set specific date
        }.time
        val expected = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

        // Act
        val result = date.toHumanDate()

        // Assert
        assertEquals(expected, result)
    }
}