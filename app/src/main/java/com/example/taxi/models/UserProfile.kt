package com.example.taxi.models

import com.example.taxi.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

data class UserProfile(
    @Serializable(with = UUIDSerializer::class) var user_id: UUID,
    var first_name: String?,
    var last_name: String?,
    var birth_date: String?,
    var gender: String?,
    var city: String?,
    var role: String
)