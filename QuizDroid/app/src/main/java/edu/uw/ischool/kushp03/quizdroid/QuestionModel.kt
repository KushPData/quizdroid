package edu.uw.ischool.kushp03.quizdroid

data class QuestionModel(
    val question: String,
    val answerChoices: List<String>,
    val correctAnswer: Int
)
