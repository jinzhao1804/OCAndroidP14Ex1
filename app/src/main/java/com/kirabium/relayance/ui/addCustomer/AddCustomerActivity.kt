package com.kirabium.relayance.ui.addCustomer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import com.kirabium.relayance.domain.model.Customer
import java.time.LocalDate
import java.util.Date
import java.util.UUID

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding
    val repository = CustomerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupToolbar()
        addNewCustomer()
    }

    private fun addNewCustomer(){
        binding.saveFab.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            // Get the current date
            val today = Date()

            repository.addCustomer(
                Customer(
                    id = generateUniqueId(), // Replace with your logic to generate a unique Int ID
                    name = name,
                    email = email,
                    createdAt = today
                )
            )
            finish()
        }
    }
    // Example function to generate a unique Int ID (replace with your actual logic)
    private fun generateUniqueId(): Int {
        return (0..Int.MAX_VALUE).random()
    }
    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}