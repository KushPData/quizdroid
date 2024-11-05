package edu.uw.ischool.kushp03.quizdroid

interface TopicRepository {
    fun getTopics(): ArrayList<TopicModel>
}