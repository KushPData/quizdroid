package edu.uw.ischool.kushp03.quizdroid
import android.app.Application
import android.content.Context
import android.util.Log

class QuizApp : Application() {
    private lateinit var topicRepository: TopicRepository
    private val tag = "QuizApp"
    override fun onCreate() {
        super.onCreate()
        Log.i(tag,"In QuizApp right now!" )
    }

    fun getTopicRepository() : TopicRepository {
        topicRepository = TopicRepositoryImplementation(this)
        return topicRepository
    }
}