package com.example.test_3

import android.os.Bundle
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

        // Visa ett första slumpmässigt kort
        updateCardImage(cardImageView)

        // Gissning: Röd (Hjärter, Ruter)
        guessRedButton.setOnClickListener {
            if (isGuessCorrect("Röd")) {
                updateScore(1, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardImageView)
        }

        // Gissning: Svart (Klöver, Spader)
        guessBlackButton.setOnClickListener {
            if (isGuessCorrect("Svart")) {
                updateScore(1, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardImageView)
        }

        // Gissning: Hjärter
        guessHeartButton.setOnClickListener {
            if (isGuessCorrect("Hjärter")) {
                updateScore(5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardImageView)
        }

        // Gissning: Ruter
        guessDiamondButton.setOnClickListener {
            if (isGuessCorrect("Ruter")) {
                updateScore(5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardImageView)
        }

        // Gissning: Klöver
        guessClubButton.setOnClickListener {
            if (isGuessCorrect("Klöver")) {
                updateScore(5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardImageView)
        }

        // Gissning: Spader
        guessSpadeButton.setOnClickListener {
            if (isGuessCorrect("Spader")) {
                updateScore(5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardImageView)
        }

        // Tillbaka-knappen
        backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Uppdatera bilden för kortet
     */
    private fun updateCardImage(cardImageView: ImageView) {
        currentCardIndex = Random.nextInt(cards.size)
        val newCard = cards[currentCardIndex]
        cardImageView.setImageResource(newCard.first)
    }

    /**
     * Kontrollera om gissningen är korrekt
     */
    private fun isGuessCorrect(guess: String): Boolean {
        val cardSuit = cards[currentCardIndex].second
        return when (guess) {
            "Röd" -> cardSuit == "Hjärter" || cardSuit == "Ruter"
            "Svart" -> cardSuit == "Klöver" || cardSuit == "Spader"
            else -> cardSuit == guess
        }
    }

    /**
     * Uppdatera poäng
     */
    private fun updateScore(value: Int, scoreTextView: TextView) {
        score += value
        scoreTextView.text = "Poäng: $score"
    }
}