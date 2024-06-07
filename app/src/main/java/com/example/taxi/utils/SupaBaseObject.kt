package com.example.taxi.utils

import android.graphics.drawable.Drawable
import com.example.taxi.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupaBaseObject {
    private var client = createSupabaseClient(
        supabaseUrl = "https://iqzbbkamhepdbllgzbpq.supabase.co",

        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlxemJia2FtaGVwZGJsbGd6YnBxIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTk2MDM4NDMsImV4cCI6MjAxNTE3OTg0M30.nON-vzAWMiY4dh_u3DhXkgVqoDd5TRMlvsDADpMrkZc"
    ) {
        install(GoTrue)
        install(Postgrest)
        install(Storage)
        //install other modules
    }

    var currentUser : User? = null

    fun getClient(): SupabaseClient {
        return client
    }

}