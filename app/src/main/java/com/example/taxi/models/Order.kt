package com.example.taxi.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderId: String = "",
    val orderDate: String = "",
    val orderTotal: Double = 0.0,
    val startLocation: String = "",
    val endLocation: String = "",
    val comment: String = "",
    val userId: String = "",
    val driverId: String? = null,
    val status: String = "pending" // "pending", "accepted", "completed"
)
