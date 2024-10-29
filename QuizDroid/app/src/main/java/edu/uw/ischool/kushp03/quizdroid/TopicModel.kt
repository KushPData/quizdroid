package edu.uw.ischool.kushp03.quizdroid

data class TopicModel(
    val topicName: String,
    val topicDescription: String,
    val questions: List<QuestionModel>
)
