package com.example.taxi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.adapters.ChatAdapter
import com.example.taxi.models.Massage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SupportFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var messageAdapter: ChatAdapter
    private val messages = mutableListOf<Massage>()
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_support, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMessages)
        editTextMessage = view.findViewById(R.id.editTextMessage)
        buttonSend = view.findViewById(R.id.buttonSend)
        database = FirebaseDatabase.getInstance().reference.child("messages")

        messageAdapter = ChatAdapter(messages)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        buttonSend.setOnClickListener {
            sendMessage()
        }

        listenForMessages()

        return view
    }

    private fun sendMessage() {
        val text = editTextMessage.text.toString()
        if (text.isNotEmpty()) {
            val messageId = database.push().key ?: "AIzaSyAvDcHdEgtNeOQ7JejgUGPRez-zaC_Lrnc"
            val message = Massage(
                id = messageId,
                text = text,
                senderId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                timestamp = System.currentTimeMillis()
            )
            database.child(messageId).setValue(message)
            editTextMessage.text.clear()
        }
    }

    private fun listenForMessages() {
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Massage::class.java)
                message?.let {
                    messages.add(it)
                    messageAdapter.notifyItemInserted(messages.size - 1)
                    recyclerView.scrollToPosition(messages.size - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}


