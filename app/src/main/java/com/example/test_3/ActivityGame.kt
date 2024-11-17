package com.example.test_3

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
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
        val cardContainer = findViewById<CardView>(R.id.cardImageContainer)
        val cardFront = findViewById<ImageView>(R.id.cardFront)
        val cardBack = findViewById<ImageView>(R.id.cardBack)

        // Initialt visa baksidan av kortet och dölja framsidan
        cardBack.visibility = View.VISIBLE
        cardFront.visibility = View.GONE

        // Lyssnare för när användaren trycker på kortet
        cardContainer.setOnClickListener {
            // Rotera kortets framsida och baksida
            val rotateFront = ObjectAnimator.ofFloat(cardFront, "rotationY", 0f, 180f)
            val rotateBack = ObjectAnimator.ofFloat(cardBack, "rotationY", 0f, 180f)

            // Sätt varaktigheten för animationen
            rotateFront.duration = 500
            rotateBack.duration = 500

            // För att synkronisera rotationen, starta dem samtidigt
            rotateFront.start()
            rotateBack.start()

            // När båda animationerna är klara, ändra synligheten
            rotateFront.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // När rotationen är klar, växla synligheten mellan framsidan och baksidan
                    if (cardBack.visibility == View.VISIBLE) {
                        cardBack.visibility = View.GONE
                        cardFront.visibility = View.VISIBLE
                    } else {
                        cardFront.visibility = View.GONE
                        cardBack.visibility = View.VISIBLE
                    }
                    // Återställ rotationen för nästa animation
                    cardFront.rotationY = 0f
                    cardBack.rotationY = 0f
                }
            })
        }

        // Visa ett första slumpmässigt kort
        updateCardImage(cardFront)

        // Gissning: Röd (Hjärter, Ruter)
        guessRedButton.setOnClickListener {
            if (isGuessCorrect("Röd")) {
                updateScore(+1, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardFront)
        }

        // Gissning: Svart (Klöver, Spader)
        guessBlackButton.setOnClickListener {
            if (isGuessCorrect("Svart")) {
                updateScore(+1, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardFront)
        }

        // Gissning: Hjärter
        guessHeartButton.setOnClickListener {
            if (isGuessCorrect("Hjärter")) {
                updateScore(+5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardFront)
        }

        // Gissning: Ruter
        guessDiamondButton.setOnClickListener {
            if (isGuessCorrect("Ruter")) {
                updateScore(+5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardFront)
        }

        // Gissning: Klöver
        guessClubButton.setOnClickListener {
            if (isGuessCorrect("Klöver")) {
                updateScore(5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardFront)
        }

        // Gissning: Spader
        guessSpadeButton.setOnClickListener {
            if (isGuessCorrect("Spader")) {
                updateScore(5, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage(cardFront)
        }

        // Tillbaka-knappen
        backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Uppdatera bilden för kortet
     */
    private fun updateCardImage(cardFront: ImageView) {
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

fun endGame(isWin: Boolean) {
    val message = if (isWin) {
        "Grattis! Du vann spelet med 21 poäng!"
    } else {
        "Spelet är över. Du förlorade eftersom din poäng ${if (score > 21) "överstiger 21" else "är under 0"}."
    }

    AlertDialog.Builder(this)
        .setTitle(if (isWin) "Du vann!" else "Spelet över")
        .setMessage(message)
        .setCancelable(false) // Gör dialogen obligatorisk att avvisa
        .setPositiveButton("Tillbaka menyn") { _, _ ->
            finish() // Avsluta spelet och återgå till menyn
        }
        .show()
}
}

