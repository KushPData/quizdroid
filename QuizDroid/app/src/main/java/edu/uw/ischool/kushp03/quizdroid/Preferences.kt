package edu.uw.ischool.kushp03.quizdroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Preferences : AppCompatActivity() {
    private lateinit var urlEditText: EditText
    private lateinit var intervalEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var backButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preferences)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        urlEditText = findViewById(R.id.newUrl)
        intervalEditText = findViewById(R.id.newInterval)
        updateButton = findViewById(R.id.update)
        backButton = findViewById(R.id.backButton)

        val sharedPreference = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val editor = sharedPreference.edit()

        updateButton.setOnClickListener {
            val newUrl = urlEditText.text.toString()
            val newInterval = intervalEditText.text.toString().toInt()

            editor.apply {
                putString("newUrl", newUrl)
                putInt("newInterval", newInterval)
                apply()
            }

            backButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            Toast.makeText(this, "Preferences Updated", Toast.LENGTH_SHORT).show()
        }

    }
}