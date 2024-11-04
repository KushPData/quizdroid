package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Button

class TopicList : Fragment() {
    private val topics = arrayListOf<TopicModel>(
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
    private var topicsArray = topics.toTypedArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topicListView = view.findViewById<LinearLayout>(R.id.topic_list_view)
//        val display = view.findViewById<TextView>(R.id.display)
        for(topic in topicsArray) {
            val quizButton = Button(context).apply {
                text = topic.topicName
                setOnClickListener {
                    val topicOverviewFragment = TopicOverview.newInstance(topic)
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, topicOverviewFragment).addToBackStack(null).commit()
                }
            }
            topicListView.addView(quizButton)
        }
    }
}