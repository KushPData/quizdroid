package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Button
import android.widget.TextView

class TopicList : Fragment() {
    private lateinit var topics: ArrayList<TopicModel>
//    private var topicsArray = topics.toTypedArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireContext().applicationContext as QuizApp
        topics = app.getTopicRepository().getTopics()
        val topicsArray = topics.toTypedArray()

        val topicListView = view.findViewById<LinearLayout>(R.id.topic_list_view)

        for(topic in topicsArray) {
            val shortDescription = TextView(context)

            shortDescription.text = topic.topicShortDescription

            val quizButton = Button(context).apply {
                text = topic.topicName
                setOnClickListener {
                    val topicOverviewFragment = TopicOverview.newInstance(topic)
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, topicOverviewFragment).addToBackStack(null).commit()
                }
            }
            topicListView.addView(shortDescription)
            topicListView.addView(quizButton)
        }
    }
}