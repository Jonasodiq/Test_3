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

        // Hantera systemfält för Edge-to-Edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Hitta back-knappen
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