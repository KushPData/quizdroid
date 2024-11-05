package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var topicRepository: TopicRepository
    private lateinit var topics: ArrayList<TopicModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        topicRepository = (application as QuizApp).getTopicRepository()
//        topics = topicRepository.getTopics()

        getTopicListFragment()
    }

    private fun getTopicListFragment() {
        val topicListFragment = TopicList()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, topicListFragment).commit()
    }
}
