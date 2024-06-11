package com.example.taxi.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.models.Massages



class ChatAdapter(private val messages: List<Massages>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CLIENT = 1
        
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CLIENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_client, parent, false)
            ClientViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_support, parent, false)
            SupportViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is ClientViewHolder) {
            holder.bind(message)
        } else if (holder is SupportViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.textMessage)

        fun bind(message: Massages) {
            messageText.text = message.text
        }
    }

    class SupportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.textMessage)

        fun bind(message: Massages) {
            messageText.text = message.text
        }
    }
}
