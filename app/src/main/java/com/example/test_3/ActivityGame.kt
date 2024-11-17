package com.example.test_3

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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

    private lateinit var cardFront: ImageView
    private lateinit var cardContainer: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Koppla layout-element
        val scoreTextView = findViewById<TextView>(R.id.scoreText)
        val guessRedButton = findViewById<Button>(R.id.guessRedButton)
        val guessBlackButton = findViewById<Button>(R.id.guessBlackButton)
        val guessHeartButton = findViewById<ImageView>(R.id.heartsIcon)
        val guessDiamondButton = findViewById<ImageView>(R.id.diamondsIcon)
        val guessClubButton = findViewById<ImageView>(R.id.clubsIcon)
        val guessSpadeButton = findViewById<ImageView>(R.id.spadesIcon)
        val backButton = findViewById<Button>(R.id.backButton)
        cardContainer = findViewById(R.id.cardImageContainer)
        cardFront = findViewById(R.id.cardFront)

        // Visa ett första slumpmässigt kort
        updateCardImage()

        // Kortanimering
        cardContainer.setOnClickListener {
            flipAnimation()
        }

        // Gissning: Röd (Hjärter, Ruter)
        guessRedButton.setOnClickListener {
            if (isGuessCorrect("Röd")) {
                updateScore(+1, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

        // Gissning: Svart (Klöver, Spader)
        guessBlackButton.setOnClickListener {
            if (isGuessCorrect("Svart")) {
                updateScore(+1, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

        // Gissning: Hjärter
        guessHeartButton.setOnClickListener {
            if (isGuessCorrect("Hjärter")) {
                updateScore(+5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

        // Gissning: Ruter
        guessDiamondButton.setOnClickListener {
            if (isGuessCorrect("Ruter")) {
                updateScore(+5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

        // Gissning: Klöver
        guessClubButton.setOnClickListener {
            if (isGuessCorrect("Klöver")) {
                updateScore(+5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

        // Gissning: Spader
        guessSpadeButton.setOnClickListener {
            if (isGuessCorrect("Spader")) {
                updateScore(+5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

        // Tillbaka-knappen
        backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Uppdatera bilden för kortet
     */
    private fun updateCardImage() {
        currentCardIndex = Random.nextInt(cards.size)
        val newCard = cards[currentCardIndex]
        cardFront.setImageResource(newCard.first)
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

        // Kontrollera om spelaren vinner eller förlorar
        when {
            score == 21 -> endGame(true) // Spelaren vinner exakt 21
            score > 21 || score < 0 -> endGame(false)  // Spelaren förlorar
        }
    }

    /**
     * Avsluta spelet
     */
    private fun endGame(isWin: Boolean) {
        val message = if (isWin) {
            "Grattis! Du vann spelet med 21 poäng!"
        } else {
            "Spelet är över. Du förlorade eftersom din poäng ${if (score > 21) "överstiger 21" else "är under 0"}."
        }

        AlertDialog.Builder(this)
            .setTitle(if (isWin) "Du vann!" else "Spelet över")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Tillbaka menyn") { _, _ ->
                finish()
            }
            .show()
    }

    /**
     * Animering för kortvändning
     */
    private fun flipAnimation() {
        val animeToFront = ObjectAnimator.ofFloat(cardContainer, "scaleX", 1f, 0f)
        val animeToBack = ObjectAnimator.ofFloat(cardContainer, "scaleX", 0f, 1f)

        animeToFront.interpolator = DecelerateInterpolator()
        animeToBack.interpolator = AccelerateInterpolator()

        animeToFront.duration = 1000
        animeToBack.duration = 1000

        animeToFront.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                // Byt bild under animeringen
                if (cardFront.drawable.constantState == resources.getDrawable(R.drawable.clubs_k).constantState) {
                    cardFront.setImageResource(R.drawable.diamonds_k)
                } else {
                    cardFront.setImageResource(R.drawable.clubs_k)
                }

                // Starta andra delen av animationen
                animeToBack.start()
            }
        })

        animeToFront.start()
    }
}
