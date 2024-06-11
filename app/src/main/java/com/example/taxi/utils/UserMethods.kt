package com.example.taxi.utils

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.taxi.models.Message
import com.example.taxi.models.Order
import com.example.taxi.models.UserProfile
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonElement
import java.util.UUID


class UserMethods {
    suspend fun Auth(mail: String, pass: String): Boolean {
        return try {
            SupaBaseObject.getClient().gotrue.loginWith(Email) {
                email = mail
                password = pass
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun SignUp(mail: String, pass: String, role: String): Boolean {
        return try {
            val result = SupaBaseObject.getClient().gotrue.signUpWith(Email) {
                email = mail
                password = pass
            }
            val userId = result.user?.id ?: return false
            SupaBaseObject.getClient().postgrest["users"]
                .insert(
                    UserProfile(
                        user_id = UUID.fromString(userId),
                        first_name = null,
                        last_name = null,
                        birth_date = null,
                        gender = null,
                        city = null,
                        role = role
                    )
                )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getUserSession(): UserInfo? {
        return try {
            SupaBaseObject.getClient().gotrue.retrieveUserForCurrentSession()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveUser(sPref: SharedPreferences.Editor, mail: String, pass: String) {
        sPref.putString("email", mail)
        sPref.putString("pass", pass)
        sPref.apply()
    }

    suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            SupaBaseObject.getClient().postgrest["users"]
                .select { eq("user_id", userId) }
                .decodeSingleOrNull<UserProfile>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createOrder(order: Order): Boolean {
        return try {
            SupaBaseObject.getClient().postgrest["orders"].insert(order)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun updateOrder(orderId: String, update: Map<String, Any>): Boolean {
        return try {
            SupaBaseObject.getClient().postgrest["orders"].update(update) { eq("orderId", orderId) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun sendMessage(message: Message): Boolean {
        return try {
            SupaBaseObject.getClient().postgrest["messages"].insert(message)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getMessages(): List<Message> {
        return try {
            SupaBaseObject.getClient().postgrest["messages"].select().decodeList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    

    }
}
