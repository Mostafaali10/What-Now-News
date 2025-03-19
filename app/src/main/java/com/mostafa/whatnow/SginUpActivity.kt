package com.mostafa.whatnow

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
import com.mostafa.whatnow.databinding.ActivitySginUpBinding

class SginUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySginUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySginUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        auth = Firebase.auth



        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            val conPass = binding.copassEt.text.toString()

            if (email.isBlank() || pass.isBlank() || conPass.isBlank())
                Toast.makeText(this, "Missing field/s", Toast.LENGTH_SHORT).show()
            else if (pass.length < 6)
                Toast.makeText(this, "Password is too short!", Toast.LENGTH_SHORT).show()
            else if (pass != conPass)
                Toast.makeText(this, "Password Don not Match", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.isVisible = true
                addUser(email, pass)
            }
        }

        binding.alreadyBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }


    }

    private fun addUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                binding.progress.isVisible = false
                if (task.isSuccessful) {
                    val name = binding.nameEt.text.toString()
                    verifyEmail()
                    Toast.makeText(this, "Signed Successfuly", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Hi, $name", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,LoginActivity::class.java))

                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check Your Email!", Toast.LENGTH_SHORT).show()
                    binding.progress.isVisible = false
                }
            }
    }
}