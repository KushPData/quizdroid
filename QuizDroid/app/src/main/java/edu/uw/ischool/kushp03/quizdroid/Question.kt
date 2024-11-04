package edu.uw.ischool.kushp03.quizdroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView


class Question : Fragment() {
    private lateinit var topic: TopicModel
    private var questionNumber: Int = 0
    private var correctAnswers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = topic.questions[questionNumber]
        view.findViewById<TextView>(R.id.question).text = question.question

        val answerOptions = view.findViewById<RadioGroup>(R.id.answer_options)
        question.answerChoices.forEach { choice ->
                val selectOption = RadioButton(context).apply {
                text = choice
                    id = View.generateViewId()
            }
            answerOptions.addView(selectOption)
        }

        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            val checkedAnswer = answerOptions.checkedRadioButtonId
            if(checkedAnswer != -1) {
                val selectedRadioButton = view.findViewById<RadioButton>(checkedAnswer)
                val selectedAnswer = answerOptions.indexOfChild(selectedRadioButton)

                if(selectedAnswer == question.correctAnswer) {
                    correctAnswers++
                }

                val answerFragment = Answer.newInstance(topic, selectedAnswer, question.correctAnswer, correctAnswers, questionNumber, topic.questions.size)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, answerFragment).addToBackStack(null).commit()
            }
        }
    }

    companion object {
        fun newInstance(topic: TopicModel, questionNumber: Int, correctAnswers: Int): Question {
            val questionFragment = Question()
            questionFragment.topic = topic
            questionFragment.questionNumber = questionNumber
            questionFragment.correctAnswers = correctAnswers
            return questionFragment
        }
    }
}