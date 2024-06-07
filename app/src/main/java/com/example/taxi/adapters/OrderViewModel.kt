package com.example.taxi.adapters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taxi.models.Order
import com.google.firebase.firestore.FirebaseFirestore

class OrderViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val ordersList = mutableListOf<Order>()
                for (document in result) {
                    val order = document.toObject(Order::class.java)
                    ordersList.add(order)
                }
                _orders.value = ordersList
            }
            .addOnFailureListener { exception ->
                Log.w("OrderViewModel", "Error getting documents: ", exception)
            }
    }
}
