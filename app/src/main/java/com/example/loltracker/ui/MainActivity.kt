package com.example.loltracker.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.loltracker.R
import androidx.navigation.ui.setupWithNavController
import com.example.loltracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ------------------------------------------------------------------------------------------------------------------------------
        // Connecting bottom navbar to the Nav Host and Nav Controller
        // ------------------------------------------------------------------------------------------------------------------------------
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}