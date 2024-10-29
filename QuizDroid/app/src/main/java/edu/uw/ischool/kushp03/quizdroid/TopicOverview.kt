package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class TopicOverview : Fragment() {
    private lateinit var topic: TopicModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.description).text = topic.topicDescription
        view.findViewById<TextView>(R.id.number_of_questions).text = "Total Number of Questions: ${topic.questions.size}"
        view.findViewById<Button>(R.id.begin_button).setOnClickListener {
            val questionFragment = Question.newInstance(topic, 0)
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, questionFragment).addToBackStack(null).commit()
        }
    }

    companion object {
        fun newInstance(topic: TopicModel): TopicOverview {
            val topicOverviewFragment = TopicOverview()
            topicOverviewFragment.topic = topic
            return topicOverviewFragment
        }
    }
}