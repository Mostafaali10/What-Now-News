package com.mostafa.whatnow.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mostafa.whatnow.Auth.LoginActivity
import com.mostafa.whatnow.R
import com.mostafa.whatnow.databinding.ActivityHomeScreenBinding


class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding // Fixed the type here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.homeScreen)


        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        favoriteArticleButton()
        generalButton()
        healthBtn()
        scienceBtn()
        sportBtn()
        technologyBtb()
        businessBtn()
    }


    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val auth: FirebaseAuth = Firebase.auth
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

    private fun favoriteArticleButton() {
        binding.favArticlesBtn.setOnClickListener {
            Toast.makeText(this, "fav icon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generalButton() {
        binding.generalBtn.setOnClickListener {
            val i = Intent(this, RecycleViewListActivity::class.java)
            startActivity(i)
        }
    }

    private fun sportBtn() {
        binding.sportBtn.setOnClickListener {
            val i = Intent(this, RecycleViewListActivity::class.java)
            i.putExtra("category", "sports")
            startActivity(i)

        }
    }


    private fun scienceBtn() {
        binding.scienceBtn.setOnClickListener {
            val i = Intent(this, RecycleViewListActivity::class.java)
            i.putExtra("category", "science")
            startActivity(i)

        }
    }

    private fun healthBtn() {
        binding.healthBtn.setOnClickListener {
            val i = Intent(this, RecycleViewListActivity::class.java)
            i.putExtra("category", "health")
            startActivity(i)
        }
    }

    private fun technologyBtb() {
        binding.technologyBtn.setOnClickListener {
            val i = Intent(this, RecycleViewListActivity::class.java)
            i.putExtra("category", "technology")
            startActivity(i)
        }
    }

    private fun businessBtn() {
        binding.businessBtn.setOnClickListener {
            val i = Intent(this, RecycleViewListActivity::class.java)
            i.putExtra("category", "business")
            startActivity(i)
        }
    }


}
