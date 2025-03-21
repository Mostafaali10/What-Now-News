package com.mostafa.whatnow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mostafa.whatnow.Activitys.HomeScreenActivity
import com.mostafa.whatnow.Auth.LoginActivity
import com.mostafa.whatnow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startApp()
    }

    private fun startApp() {
        val auth: FirebaseAuth = Firebase.auth
        if (auth.currentUser == null) {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finishAffinity()

        } else if (auth.currentUser!!.isEmailVerified) {
            val i = Intent(this, HomeScreenActivity::class.java)
            startActivity(i)
            finishAffinity()
        } else {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            Toast.makeText(this, "verify the email", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }
    }

}