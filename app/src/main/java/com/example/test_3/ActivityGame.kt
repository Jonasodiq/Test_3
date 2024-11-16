package com.example.test_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class ActivityGame : AppCompatActivity() {

    private val cards = listOf(
        Pair(R.drawable.hearts_k, "Hjärter"),
        Pair(R.drawable.diamonds_k, "Ruter"),
        Pair(R.drawable.clubs_k, "Klöver"),
        Pair(R.drawable.spades_k, "Spader")
    )

    private var score = 0
    private var currentCardIndex = 0

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