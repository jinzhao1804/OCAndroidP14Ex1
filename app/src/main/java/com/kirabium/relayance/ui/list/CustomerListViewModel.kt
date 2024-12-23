package com.kirabium.relayance.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerListViewModel@Inject constructor(
    private val customerRepository: CustomerRepository): ViewModel() {

    private val _customers = MutableLiveData<List<Customer>>()
    val customers: LiveData<List<Customer>> = _customers

    fun fetchAllCustomers() {
        viewModelScope.launch {
            _customers.value = customerRepository.getCustomers()
        }
    }
}