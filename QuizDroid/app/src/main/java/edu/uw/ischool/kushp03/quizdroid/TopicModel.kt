package edu.uw.ischool.kushp03.quizdroid
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicModel(
    val topicName: String,
    val topicShortDescription: String,
    val topicDescription: String,
    val questions: List<QuestionModel>
): Parcelable
