package com.kirabium.relayance.ui.list
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirabium.relayance.databinding.ActivityMainBinding
import com.kirabium.relayance.ui.addCustomer.AddCustomerActivity
import com.kirabium.relayance.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customerAdapter: CustomerListAdapter

    private val customerListViewModel: CustomerListViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setupBinding()
        setupCustomerRecyclerView()
        setupFab()
    }

    private fun setupFab() {
        binding.addCustomerFab.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupCustomerRecyclerView() {
        // Set up RecyclerView layout manager
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an empty list
        customerAdapter = CustomerListAdapter(emptyList()) { customer ->
            // Handle item clicks
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customer.id)
            }
            startActivity(intent)
        }

        // Set the adapter to the RecyclerView
        binding.customerRecyclerView.adapter = customerAdapter

        // Observe customers LiveData and update the adapter's data
        customerListViewModel.customers.observe(this) { customers ->
            customerAdapter.updateData(customers)
        }

        customerListViewModel.fetchAllCustomers()

    }


    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
