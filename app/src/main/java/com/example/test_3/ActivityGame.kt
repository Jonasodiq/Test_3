package com.example.test_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.widget.Button

class ActivityGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        // Koppla layout-element
        val cardImageView = findViewById<ImageView>(R.id.cardImage)
        val scoreTextView = findViewById<TextView>(R.id.scoreText)
        val guessRedButton = findViewById<Button>(R.id.guessRedButton)
        val guessBlackButton = findViewById<Button>(R.id.guessBlackButton)
        val guessHeartButton = findViewById<ImageView>(R.id.heartsIcon)
        val guessDiamondButton = findViewById<ImageView>(R.id.diamondsIcon)
        val guessClubButton = findViewById<ImageView>(R.id.clubsIcon)
        val guessSpadeButton = findViewById<ImageView>(R.id.spadesIcon)
        val backButton = findViewById<Button>(R.id.backButton)

        // Lägg till en click listener på Back-knappen för att navigera tillbaka
        backButton.setOnClickListener {
            onBackPressed() // Använd systemets standard-funktion för att hantera tillbaka
        }
    }

    // Om du vill hantera systemets back-knapp (fysiskt eller på skärmen):
    override fun onBackPressed() {
        super.onBackPressed() // Detta kommer avsluta den aktuella aktiviteten och återgå till föregående aktivitet
    }
}