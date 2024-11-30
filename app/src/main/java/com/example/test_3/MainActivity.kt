package com.example.test_3

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        try {
            val playButton = findViewById<MaterialButton>(R.id.playButton)
            val settingsButton = findViewById<Button>(R.id.settingsButton)
            val aboutButton = findViewById<Button>(R.id.aboutButton)

            // ClickListener
            playButton.setOnClickListener {
                navigateToGameActivity()
            }

            settingsButton.setOnClickListener {
                openSettings()
            }

            aboutButton.setOnClickListener {
                showAboutDialog()
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error during initialization: ${e.message}", e)
        }
    }
    // ActivityGame
    private fun navigateToGameActivity() {
        try {
            val intent = Intent(this, ActivityGame::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Could not start ActivityGame: ${e.message}", e)
        }
    }

    // Opens settings
    private fun openSettings() {
        try {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.i(TAG, "Could not start SettingsActivity.")
        }
    }

    // Display dialog about the app
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("About the Game")
            .setMessage("This app is a game where you guess the suit or suit of the card. \n+1 point for the correct color.\n+5 points for the right suite.\n-1 for mistakes.\n21 points for win If you have above than 21 points you lose. Have fun!")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
