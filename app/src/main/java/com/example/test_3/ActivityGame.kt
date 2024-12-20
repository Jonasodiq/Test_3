package com.example.test_3

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
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

            // Vänta en stund innan vi döljer kortet igen
            cardContainer.postDelayed({
                flipToBack()
            }, 1000)
        }
    }

    // Uppdatera bilden för kortet
    private fun updateCardImage() {
        currentCardIndex = Random.nextInt(cards.size)
        val newCard = cards[currentCardIndex]
        cardFront.setImageResource(newCard.first) // Ställ in bilden för det nya kortet
    }

    // Kontrollera om gissningen är korrekt
    private fun isGuessCorrect(guess: String): Boolean {
        val cardSuit = cards[currentCardIndex].second
        return when (guess) {
              "Red" -> cardSuit == "Hearts" || cardSuit == "Diamonds"
            "Black" -> cardSuit == "Clover" || cardSuit == "Spades"
              else  -> cardSuit == guess
        }
    }

    // Uppdatera poäng
    private fun updateScore(value: Int, scoreTextView: TextView) {
        score += value
        scoreTextView.text = "$score"

        // Kontrollera om spelaren vinner eller förlorar
        when {
            score == 21 -> endGame(true) // Spelaren vinner exakt 21
            score  > 21 || score < 0 -> endGame(false)  // Spelaren förlorar
        }
    }

    // Avsluta spelet
    private fun endGame(isWin: Boolean) {
        val message = if (isWin) {
            "Congratulations! You won the game with 21 points!"
        } else {
            "The game is over. You lost because your score ${if (score > 21) "exceeds 21" else "is below 0"}."
        }

        AlertDialog.Builder(this)
            .setTitle(if (isWin) "You won!" else "Game over")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Menu") { _, _ ->
                finish()
            }
            .show()
    }

    // Animation
    private fun flipToFront(onFlipEnd: () -> Unit) {
        val animeToFront = ObjectAnimator.ofFloat(cardContainer, "scaleX", 1f, 0f)
        val animeToBack = ObjectAnimator.ofFloat(cardContainer, "scaleX", 0f, 1f)

        animeToFront.interpolator = DecelerateInterpolator()
        animeToBack.interpolator = AccelerateInterpolator()

        animeToFront.duration = 300
        animeToBack.duration = 300

        animeToFront.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                // Välj ett nytt kort och visa dess bild
                updateCardImage()

                // Starta nästa del av animationen
                animeToBack.start()
            }
        })

        animeToBack.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                // Kör vidare logik efter att framsidan visas
                onFlipEnd()
            }
        })

        animeToFront.start()
    }


    // Till baka
    private fun flipToBack() {
        val animeToFront = ObjectAnimator.ofFloat(cardContainer, "scaleX", 1f, 0f)
        val animeToBack = ObjectAnimator.ofFloat(cardContainer, "scaleX", 0f, 1f)

        animeToFront.interpolator = DecelerateInterpolator()
        animeToBack.interpolator = AccelerateInterpolator()

        animeToFront.duration = 300
        animeToBack.duration = 300

        animeToFront.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                // Visa baksidan av kortet
                cardFront.setImageResource(R.drawable.back)

                // Starta nästa del av animationen
                animeToBack.start()
            }
        })

        animeToFront.start()
    }
}
