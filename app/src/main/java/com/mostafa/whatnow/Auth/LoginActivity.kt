package com.mostafa.whatnow.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mostafa.whatnow.NewsApi.MainActivity
import com.mostafa.whatnow.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //  Firebase Auth
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()

            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(this, "Missing field/s", Toast.LENGTH_SHORT).show()
            } else {
                binding.progress.isVisible = true
                login(email, pass)
            }
        }

        binding.forgetPass.setOnClickListener {
            startActivity(Intent(this, PaasswordResetActivity::class.java))
        }

        binding.createoneBtn.setOnClickListener {
            startActivity(Intent(this, SginUpActivity::class.java))
        }


    }

    private fun login(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified) {
                        Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                    } else
                        Toast.makeText(this, "Verify Your Email !!!", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this, "Not User", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Please Sign Up", Toast.LENGTH_SHORT).show()
                    binding.progress.isVisible = false
                }
            }

    }


}