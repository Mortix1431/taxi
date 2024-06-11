package com.example.taxi.utils

import android.graphics.drawable.Drawable
import com.example.taxi.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object SupaBaseObject {
    private val client = createSupabaseClient(
        supabaseUrl = "https://your-project-id.supabase.co",
        supabaseKey = "your-supabase-key"
    ) {
        install(Postgrest)
        install(Realtime)
        install(Storage)
    }

    fun getClient(): SupabaseClient {
        return client
    }
}
