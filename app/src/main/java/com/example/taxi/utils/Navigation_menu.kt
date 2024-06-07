package com.example.taxi.utils

import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.taxi.R
import com.example.taxi.fragments.DriveFragment
import com.example.taxi.fragments.HystoryFragment
import com.example.taxi.fragments.StuffFragment

import com.google.android.material.navigation.NavigationView
import android.Manifest
import android.content.ClipData.Item
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.taxi.fragments.AddOrderFragment

import com.example.taxi.fragments.SupportFragment
import com.example.taxi.fragments.UserProfileFragment

import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider

class Navigation_menu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sadd)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DriveFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_drive)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        when (item.itemId) {
            R.id.nav_drive -> {supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DriveFragment()).commit()
                toolbar.setTitle("Такси")
            }
            R.id.nav_history -> {supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HystoryFragment()).commit()
            toolbar.setTitle("История заказов")
            }
            R.id.nav_stuff-> {supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, StuffFragment()).commit()
                toolbar.setTitle("Безопасность")
            }
            R.id.nav_chat -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SupportFragment()).commit()
                toolbar.setTitle("Служба поддержки")
            }
            R.id.nav_test -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AddOrderFragment()).commit()
                toolbar.setTitle("Добавить заказ test")
            }
            R.id.nav_profile -> {supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UserProfileFragment()).commit()
                toolbar.setTitle("Профиль")
            }

            R.id.nav_logout -> Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}