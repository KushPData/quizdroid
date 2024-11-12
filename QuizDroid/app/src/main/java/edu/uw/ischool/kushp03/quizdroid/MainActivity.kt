package edu.uw.ischool.kushp03.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var goToPreferences: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getTopicListFragment()

        goToPreferences = findViewById(R.id.goToPreferences)

        goToPreferences.setOnClickListener {
            val intent = Intent(this, Preferences::class.java)
            startActivity(intent)
        }
    }

    private fun getTopicListFragment() {
        val topicListFragment = TopicList()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, topicListFragment).commit()
    }
}
