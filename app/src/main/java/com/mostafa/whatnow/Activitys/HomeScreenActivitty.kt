package com.mostafa.whatnow.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mostafa.whatnow.Auth.LoginActivity
import com.mostafa.whatnow.R
import com.mostafa.whatnow.databinding.ActivityHomeScreenActivittyBinding


class HomeScreenActivitty : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenActivittyBinding // Fixed the type here
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenActivittyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Add click listeners for categories if needed
        // Example: binding.cardGeneral.setOnClickListener { ... }
    }


    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
                R.id.action_logout -> {
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                R.id.action_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.action_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }
