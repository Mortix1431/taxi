package com.example.taxi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.models.Order

class OrderAdapter(
    private val orderList: List<Order>,
    private val acceptOrder: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderId: TextView = itemView.findViewById(R.id.order_id)
        val orderDate: TextView = itemView.findViewById(R.id.order_date)
        val orderTotal: TextView = itemView.findViewById(R.id.order_total)
        val acceptButton: Button = itemView.findViewById(R.id.accept_button)

        fun bind(order: Order, acceptOrder: (Order) -> Unit) {
            orderId.text = order.orderId
            orderDate.text = order.orderDate
            orderTotal.text = order.orderTotal.toString()
            acceptButton.setOnClickListener {
                acceptOrder(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order, acceptOrder)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}
