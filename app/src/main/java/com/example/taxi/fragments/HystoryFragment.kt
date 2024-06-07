package com.example.taxi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.adapters.OrderAdapter
import com.example.taxi.adapters.OrderViewModel
import com.example.taxi.models.Order
import com.google.firebase.appcheck.internal.util.Logger.TAG


class HystoryFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hystory, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_orders)
        recyclerView.layoutManager = LinearLayoutManager(context)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        orderViewModel.orders.observe(viewLifecycleOwner, Observer { orders ->
            orderAdapter = OrderAdapter(orders)
            recyclerView.adapter = orderAdapter
        })
    }
}
