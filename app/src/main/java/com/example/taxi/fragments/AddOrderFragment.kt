package com.example.taxi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taxi.R
import com.example.taxi.models.Order
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore

class AddOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_order, container, false)
        val addButton = view.findViewById<MaterialButton>(R.id.add_order_button)

        addButton.setOnClickListener {
            addOrder("12345", "2024-06-04", 150.0)
        }

        return view
    }

    private fun addOrder(orderId: String, orderDate: String, orderTotal: Double) {
        val db = FirebaseFirestore.getInstance()
        val order = Order(orderId, orderDate, orderTotal)
        db.collection("orders")
            .add(order)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    companion object {
        private const val TAG = "AddOrderFragment"
    }
}
