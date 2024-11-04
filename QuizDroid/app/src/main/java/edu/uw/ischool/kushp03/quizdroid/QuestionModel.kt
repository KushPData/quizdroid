package edu.uw.ischool.kushp03.quizdroid
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    val question: String,
    val answerChoices: List<String>,
    val correctAnswer: Int
): Parcelable
