plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.taxi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.taxi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}


dependencies {

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation ("androidx.activity:activity-compose:1.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation  ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.4.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    implementation ("androidx.compose.ui:ui:1.4.3")
    implementation ("androidx.compose.material:material:1.4.3")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation (platform ("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-storage")
    implementation ("com.yandex.android:maps.mobile:4.5.0-full")
    implementation(libs.androidx.core.ktx)
    implementation(platform("io.github.jan-tennert.supabase:bom:1.4.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("com.google.firebase:firebase-auth")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.material3.android)
    implementation ("androidx.fragment:fragment-ktx:1.4.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation(libs.play.services.cast.framework)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}