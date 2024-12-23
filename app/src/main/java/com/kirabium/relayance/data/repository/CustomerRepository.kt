package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer

class CustomerRepository: Repository {
    override fun getCustomers(): List<Customer> {
        return DummyData.customers
    }

    override fun addCustomer(customer: Customer) {
        DummyData.addCustomer(customer)
    }

    override fun getCustomerDetails(id: Int): Customer? {
        return DummyData.getCustomerById(id)

    }
}