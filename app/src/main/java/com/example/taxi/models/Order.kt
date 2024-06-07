package com.example.taxi.models

data class Order(
    val orderId: String = "",
    val orderDate: String = "",
    val orderTotal: Double = 0.0
)