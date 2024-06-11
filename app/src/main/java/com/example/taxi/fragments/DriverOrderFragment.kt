package com.example.taxi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.adapters.OrderAdapter
import com.example.taxi.adapters.TaxiOrderViewModel
import com.example.taxi.models.Order
import com.example.taxi.utils.UserMethods
import kotlinx.coroutines.launch

class DriverOrderFragment : Fragment() {
    private val viewModel: TaxiOrderViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_driver_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_orders)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.orders.observe(viewLifecycleOwner, { orders ->
            orderAdapter = OrderAdapter(orders) { order ->
                acceptOrder(order) }
            recyclerView.adapter = orderAdapter
        })
    }

    private fun acceptOrder(order: Order) {
        lifecycleScope.launch {
            val userSession = UserMethods().getUserSession()
            if (userSession != null && order.driverId == null && order.status == "pending") {
                val updatedOrder = order.copy(driverId = userSession.id, status = "accepted")
                UserMethods().updateOrder(order.orderId, mapOf("driverId" to userSession.id, "status" to "accepted"))
                viewModel.fetchOrders()
                Toast.makeText(context, "Заказ принят", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Не удалось принять заказ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

