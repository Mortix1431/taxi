package com.example.taxi.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taxi.models.Order
import com.example.taxi.utils.SupaBaseObject
import com.yandex.mapkit.geometry.Point
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaxiOrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    init {
        fetchOrders()
    }

    fun fetchOrders() {
        viewModelScope.launch {
            try {
                val orders = SupaBaseObject.getClient().postgrest["orders"].select().decodeList<Order>()
                _orders.postValue(orders)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

