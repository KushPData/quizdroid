package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class Answer : Fragment() {
    private lateinit var topic: TopicModel
    private var selectedAnswer: Int = -1
    private var correctAnswer: Int = -1
    private var correctAnswers: Int = 0
    private var questionNumber: Int = 0
    private var totalQuestions: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.user_answer).text = "Your answer: $selectedAnswer"
        view.findViewById<TextView>(R.id.correct_answer).text = "Correct answer: $correctAnswer"
        view.findViewById<TextView>(R.id.score).text = "You have $correctAnswers out of $totalQuestions correct"

        val nextOrFinishButton = view.findViewById<Button>(R.id.next_or_finish_button)
        if (questionNumber < totalQuestions - 1) {
            nextOrFinishButton.text = "Next"
            nextOrFinishButton.setOnClickListener {
                val questionFragment = Question.newInstance(topic, questionNumber + 1)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, questionFragment).addToBackStack(null).commit()
            }
        } else {
            nextOrFinishButton.text = "Finish"
            nextOrFinishButton.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    companion object {
        fun newInstance(topic: TopicModel, selectedAnswer: Int, correctAnswer: Int, correctAnswers: Int, questionNumber: Int, totalQuestions: Int): Answer {
            val answerFragment = Answer()
            answerFragment.topic = topic
            answerFragment.selectedAnswer = selectedAnswer
            answerFragment.correctAnswer = correctAnswer
            answerFragment.correctAnswers = correctAnswers
            answerFragment.questionNumber = questionNumber
            answerFragment.totalQuestions = totalQuestions
            return answerFragment
        }
    }
}