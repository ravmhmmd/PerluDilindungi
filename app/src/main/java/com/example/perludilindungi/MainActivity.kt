package com.example.perludilindungi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.perludilindungi.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var floatingActionButton: FloatingActionButton

    fun getContextAct(): Context {
        var context: Context = this@MainActivity

        return context
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        bottomNavigation = binding.bottomNavigationMain
        bottomNavigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.news_menu -> {
                    this.findNavController(R.id.navHostFragment).navigate(R.id.newsFragment)
                    true
                }
                R.id.location_menu -> {
                    this.findNavController(R.id.navHostFragment).navigate(R.id.facilityListFragment)
                    true
                }
                R.id.bookmark_menu-> {
                    this.findNavController(R.id.navHostFragment).navigate(R.id.bookmarkListFragment)
                    true
                }
                else -> false
            }
        }

        floatingActionButton = binding.fab
        floatingActionButton.setOnClickListener {
            this.findNavController(R.id.navHostFragment).navigate(R.id.checkInFragment)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}