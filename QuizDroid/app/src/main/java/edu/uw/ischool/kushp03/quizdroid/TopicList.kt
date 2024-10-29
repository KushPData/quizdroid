package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"


class TopicList : Fragment() {
    private var topics: List<TopicModel> = emptyList()
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

    companion object {
        fun newInstance(topics: List<TopicModel>): TopicList {
            val topicListFragment = TopicList()
            topicListFragment.topics = topics
            return topicListFragment
        }
    }
}