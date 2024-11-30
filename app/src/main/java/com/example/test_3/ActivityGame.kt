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
        Pair(R.drawable.hearts_k, "Hearts"),
        Pair(R.drawable.diamonds_k, "Diamonds"),
        Pair(R.drawable.clubs_k, "Clover"),
        Pair(R.drawable.spades_k, "Spades"),
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
        cardFront.setImageResource(R.drawable.back) // Visa baksidan som standard

        // Hantera knapptryck
        guessRedButton.setOnClickListener {
            handleButtonClick("Red", +1, scoreTextView)
        }
        guessBlackButton.setOnClickListener {
            handleButtonClick("Black", +1, scoreTextView)
        }
        guessHeartButton.setOnClickListener {
            handleButtonClick("Hearts", +5, scoreTextView)
        }
        guessDiamondButton.setOnClickListener {
            handleButtonClick("Diamonds", +5, scoreTextView)
        }
        guessClubButton.setOnClickListener {
            handleButtonClick("Clover", +5, scoreTextView)
        }
        guessSpadeButton.setOnClickListener {
            handleButtonClick("Spades", +5, scoreTextView)
        }

        backButton.setOnClickListener { finish() }
    }

    // Klick metod
    private fun handleButtonClick(guess: String, scoreIncrement: Int, scoreTextView: TextView) {
        flipToFront {
            if (isGuessCorrect(guess)) {
                updateScore(scoreIncrement, scoreTextView)
            } else {
                updateScore(-1, scoreTextView)
            }
            updateCardImage()
        }

            // Vänta en stund innan vi döljer kortet igen
            cardContainer.postDelayed({
                flipToBack()
            }, 1000)
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
