package com.example.taxi.models

import com.example.taxi.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Message(
    val messageId: Int? = null,
    val text: String?,
    @Serializable(with = UUIDSerializer::class) var userId: UUID,
    val timestamp: Long = System.currentTimeMillis()
)
