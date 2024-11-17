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

            // Sätt upp klickhanterare
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
            Log.e(TAG, "Fel vid initialisering: ${e.message}", e)
        }
    }
    /**
     * Navigerar till ActivityGame
     */
    private fun navigateToGameActivity() {
        try {
            val intent = Intent(this, ActivityGame::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Kunde inte starta ActivityGame: ${e.message}", e)
        }
    }

    /**
     * Öppnar inställningar (kommande funktionalitet)
     */
    private fun openSettings() {
        try {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.i(TAG, "IKunde inte starta SettingsActivity.")
        }
    }

    /**
     * Visar en enkel dialog om appen
     */
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Om Spelet")
            .setMessage("Den här appen är ett spel där du gissar kortets färg eller svit. Ha kul!")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
