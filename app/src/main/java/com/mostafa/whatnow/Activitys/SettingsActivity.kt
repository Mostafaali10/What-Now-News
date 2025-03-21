package com.mostafa.whatnow.Activitys

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mostafa.whatnow.R
import com.mostafa.whatnow.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    // Binding object to access the layout views
    private lateinit var binding: ActivitySettingsBinding

    // List of countries with their display names and News API country codes
    private val countries = listOf(
        Pair("United States", "us"),
        Pair("Germany", "de"),
        Pair("Egypt","eg"),
        Pair("Great Britain", "gb"),
        Pair("Canada", "ca")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button

        // Handle back button click
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Adapter Function
        setupCountrySpinner()

        // Load the previously selected country from SharedPreferences
        loadSavedCountry()

        // Save the selected country when the button is clicked
        binding.saveButton.setOnClickListener {
            saveSelectedCountry()
        }

        // ---------------- Animation Part  Add a scale animation to the button on click -------------------------------------------
        val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.button_scale)
        binding.saveButton.startAnimation(scaleAnim)

        // Add animation to the Spinner
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.countrySpinner.startAnimation(fadeIn)
    }


    // Function to set up the Spinner with country names
    private fun setupCountrySpinner() {
        // Get only the country names (e.g., "United States", "Great Britain", "Canada")
        val countryNames = countries.map { it.first }

        // Create an adapter to display the country names in the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the Spinner
        binding.countrySpinner.adapter = adapter
    }

    // Function to load the previously selected country from SharedPreferences
    private fun loadSavedCountry() {
        // Access the SharedPreferences file named "NewsPrefs"
        val prefs = getSharedPreferences("NewsPrefs", Context.MODE_PRIVATE)

        // Get the saved country code, default to "us" if none is saved
        val savedCountryCode = prefs.getString("selected_country", "us")

        // Find the index of the saved country in the list
        val savedCountryIndex = countries.indexOfFirst { it.second == savedCountryCode }

        // If the saved country is found, select it in the Spinner
        if (savedCountryIndex != -1) {
            binding.countrySpinner.setSelection(savedCountryIndex)
        }
    }

    // Function to save the selected country to SharedPreferences
    private fun saveSelectedCountry() {
        // Get the position of the selected country in the Spinner
        val selectedPosition = binding.countrySpinner.selectedItemPosition

        // Get the country code of the selected country (e.g., "us", "gb", "ca")
        val selectedCountryCode = countries[selectedPosition].second

        // Access the SharedPreferences file named "NewsPrefs"
        val prefs = getSharedPreferences("NewsPrefs", Context.MODE_PRIVATE)

        // Save the selected country code to SharedPreferences
        prefs.edit().apply {
            putString("selected_country", selectedCountryCode)
            apply() // Save the changes
        }

        // Show a message to the user confirming the save
        Toast.makeText(
            this,
            "Country saved: ${countries[selectedPosition].first}",
            Toast.LENGTH_SHORT
        ).show()

    }
}