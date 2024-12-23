package com.kirabium.relayance.data.repository

import com.kirabium.relayance.domain.model.Customer

interface Repository {

     fun getCustomers(): List<Customer>
     fun addCustomer(customer: Customer)
     fun getCustomerDetails(id: Int): Customer?

}