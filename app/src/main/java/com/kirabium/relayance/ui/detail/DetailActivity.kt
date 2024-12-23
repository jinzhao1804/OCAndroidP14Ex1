package com.kirabium.relayance.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.ui.detail.composable.DetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1)

        if (customerId == -1) {
            println("Invalid Customer ID")
            finish() // Close activity if no valid ID is passed
            return
        }

        // Observe customer data
        detailViewModel.customer.observe(this) { customer ->
            if (customer != null) {
                setContent {
                    DetailScreen(customer = customer) {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            } else {
                println("Customer not found")
                finish() // Close activity if customer is not found
            }
        }

        // Fetch customer by ID
        detailViewModel.fetchCustomerById(customerId)
    }
}
