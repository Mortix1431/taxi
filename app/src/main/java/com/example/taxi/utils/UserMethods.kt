package com.example.taxi.utils

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.taxi.models.User
import com.example.taxi.models.UserProfile
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.JsonElement
import java.util.UUID

class UserMethods {

    suspend fun Auth(mail : String, pass : String) : Boolean{
        try {
            SupaBaseObject.getClient().gotrue.loginWith(Email) {
                email = mail
                password = pass
            }
            return true
        }catch (_:Exception){
            return false
        }
    }

    suspend fun SignUp(mail : String, pass : String, ) : Boolean{
        try {
            SupaBaseObject.getClient().gotrue.signUpWith(Email) {
                email = mail
                password = pass
            }
        }catch (_:Exception){
            return false
        }
        SupaBaseObject.getClient().postgrest["users"]
            .insert(
                UserProfile(
                    UUID.fromString(getUserSession()!!.id),
            null,
            null,
            null,
            null,
            null,
            null,
            1
            )
            )
        return true
    }

    suspend fun getUserSession() : UserInfo?{
        val result : UserInfo? = try{
            SupaBaseObject.getClient().gotrue.retrieveUserForCurrentSession()
        } catch (_:Exception){
            null
        }
        return result
    }


    fun saveUser(sPref : SharedPreferences.Editor, mail : String, pass : String){
        sPref.putString("email", mail)
        sPref.putString("pass", pass)
        sPref.apply()
    }


}