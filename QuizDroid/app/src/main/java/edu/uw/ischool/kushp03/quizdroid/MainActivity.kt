package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val topics = listOf<TopicModel>(
        TopicModel("Math", "Basic addition problems", listOf<QuestionModel>(
            QuestionModel("2 + 3 = ?", listOf<String>("2", "4", "5", "10"), 2),
            QuestionModel("20 + 22 = ?", listOf<String>("52", "42", "62", "32"), 1),
            QuestionModel("321 + 120 = ?", listOf<String>("441", "449", "451", "472"), 0)
        )),
        TopicModel("Physics", "Quiz on forces", listOf<QuestionModel>(
            QuestionModel("What force caused Newton's apple to fall down?", listOf<String>("Centripetal Force", "Gravitational Force", "Centrifugal Force", "Frictional Force"), 1),
            QuestionModel("Which is the weakest of the four fundamental forces?", listOf<String>("Gravitational Force", "Electromagnetic Force", "Weak Nuclear Force", "Strong Nuclear Force"), 0),
            QuestionModel("What is the SI unit of force?", listOf<String>("m", "J", "K", "N"), 3)
        )),
        TopicModel("Marvel Super Heroes", "Testing your super hero knowledge", listOf<QuestionModel>(
            QuestionModel("What type of doctor is Doctor Strange?", listOf<String>("Dentist", "Neurosurgeon", "Ophthalmologist", "Urologist"), 1),
            QuestionModel("Where was Captain America born?", listOf<String>("Brooklyn, NY", "Seattle, WA", "Boston, MA", "Detroit, MI"), 0),
            QuestionModel("Who was able to pick up Thor's hammer in Endgame?", listOf<String>("Iron Man", "Spider-Man", "Captain America", "Black Widow"), 2)
        ))
    )

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
    }

    private fun getTopicListFragment() {
        val topicListFragment = TopicList.newInstance(topics)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, topicListFragment).commit()
    }
}