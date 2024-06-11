package com.example.taxi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.R
import com.example.taxi.adapters.ChatAdapter
import com.example.taxi.models.Message

import com.example.taxi.models.UserProfile
import com.example.taxi.utils.SupaBaseObject
import com.example.taxi.utils.UserMethods
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class SupportChatFragment : Fragment() {
    private lateinit var messageAdapter: ChatAdapter
    private val messages = mutableListOf<Message>()
    private var userProfile: UserProfile? = null
    private var userId: UUID? = null
    private val userMethods = UserMethods()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val editTextMessage: TextInputEditText = view.findViewById(R.id.editTextMessage)
        val buttonSend: MaterialButton = view.findViewById(R.id.buttonSend)

        messageAdapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = messageAdapter

        lifecycleScope.launch {
            userMethods.getUserSession()?.let { userSession ->
                val userIdString = userSession.id
                fetchUserProfile(userIdString) { fetchedUserId ->
                    userId = fetchedUserId

                    buttonSend.setOnClickListener {
                        val messageText = editTextMessage.text.toString()
                        if (messageText.isNotBlank() && userId != null) {
                            val message = Message(messageId = null, text = messageText, userId = userId!!)
                            sendMessageToSupabase(message)
                            messages.add(message)
                            messageAdapter.notifyItemInserted(messages.size - 1)
                            recyclerView.scrollToPosition(messages.size - 1)
                            editTextMessage.text?.clear()
                        }
                    }

                    fetchMessagesFromSupabase()
                }
            }
        }
    }

    private fun fetchUserProfile(userIdString: String, callback: (UUID) -> Unit) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = SupaBaseObject.getClient().postgrest["users"]
                        .select { eq("user_id", userIdString) }
                        .decodeSingleOrNull<UserProfile>()
                    withContext(Dispatchers.Main) {
                        userProfile = response
                        response?.user_id?.let { callback(it) }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun sendMessageToSupabase(message: Message) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    SupaBaseObject.getClient().postgrest["messages"].insert(message.copy(messageId = null))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun fetchMessagesFromSupabase() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = SupaBaseObject.getClient().postgrest["messages"]
                        .select()
                        .decodeList<Message>()
                    withContext(Dispatchers.Main) {
                        messages.clear()
                        messages.addAll(response)
                        messageAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
