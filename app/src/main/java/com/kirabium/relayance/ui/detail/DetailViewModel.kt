package com.kirabium.relayance.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _customer = MutableLiveData<Customer?>()
    val customer: LiveData<Customer?> get() = _customer

    fun fetchCustomerById(customerId: Int) {
        val customer = repository.getCustomers().find { it.id == customerId }
        _customer.value = customer
    }
}
