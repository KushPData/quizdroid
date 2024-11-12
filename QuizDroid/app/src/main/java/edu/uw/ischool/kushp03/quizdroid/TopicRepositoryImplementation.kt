package edu.uw.ischool.kushp03.quizdroid

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.IOException

class TopicRepositoryImplementation(private val context: Context) : TopicRepository {
    private val hardcodedTopics = arrayListOf<TopicModel>(
        TopicModel("Science!", "Because SCIENCE!", "Because SCIENCE!", listOf<QuestionModel>(
            QuestionModel("What is fire?", listOf<String>("One of the four classical elements", "A magical reaction given to us by God", "A band that hasn't yet been discovered", "Fire! Fire! Fire! heh-heh"), 1)
        )),
        TopicModel("Marvel Super Heroes", "Avengers, Assemble!", "Avengers, Assemble!", listOf<QuestionModel>(
            QuestionModel("Who is Iron Man?", listOf<String>("Tony Stark", "Obadiah Stane", "A rock hit by Megadeth", "Nobody knows"), 1),
            QuestionModel("Who founded the X-men?", listOf<String>("Tony Stark", "Professor X", "The X-Institute", "Erik Lensherr"), 2),
            QuestionModel("How did Spider-Man get his powers?", listOf<String>("He was bitten by a radioactive spider", "He ate a radioactive spider", "He is a radioactive spider", "He looked at a radioactive spider"), 1)
        )),
        TopicModel("Mathematics", "Did you pass the third grade?", "Did you pass the third grade?", listOf<QuestionModel>(
            QuestionModel("What is 2+2?", listOf<String>("4", "22", "An irrational number", "Nobody knows"), 1)
        ))
    )


    override fun getTopics() : ArrayList<TopicModel> {

//        Making a json file using hardcodedTopics
        addTopicsToFile(hardcodedTopics)

        val topics = arrayListOf<TopicModel>()

        try {
            val fileInputStream: FileInputStream = context.openFileInput("questions.json")
            val jsonString = InputStreamReader(fileInputStream).readText()
            val jsonArray = JSONArray(jsonString)

            for(i in 0 until jsonArray.length()) {
                val topicObject = jsonArray.getJSONObject(i)
                val title = topicObject.getString("title")
                val desc = topicObject.getString("desc")
                val questionsArray = topicObject.getJSONArray("questions")

                val questions = mutableListOf<QuestionModel>()

                for(j in 0 until questionsArray.length()) {
                    val questionObject = questionsArray.getJSONObject(j)
                    val questionText = questionObject.getString("text")
//                    subtracting 1 from the json file answer because numbering starts from 0 instead of 1
                    val answer = questionObject.getString("answer").toInt() - 1
                    val answers = mutableListOf<String>()

                    val answersArray = questionObject.getJSONArray("answers")
                    for (k in 0 until answersArray.length()) {
                        val answerText = answersArray.getString(k)
                        answers.add(answerText)
                    }

                    questions.add(QuestionModel(questionText, answers, answer))

                }

//                Using desc for both short and long description
                topics.add(TopicModel(title, desc, desc, questions))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return topics
    }

    private fun addTopicsToFile(topics: List<TopicModel>) {
        try {
            val jsonArray = JSONArray()

            for (topic in topics) {
                val topicObject = JSONObject()
                topicObject.put("title", topic.topicName)
//                Once again, using short description and long description with the same value
                topicObject.put("desc", topic.topicShortDescription)

                val questionsArray = JSONArray()

                for(question in topic.questions) {
                    val questionObject = JSONObject()

                    questionObject.put("text", question.question)
                    questionObject.put("answer", question.correctAnswer.toString())

                    val answersArray = JSONArray()

                    for(answer in question.answerChoices) {
                        answersArray.put(answer)
                    }

                    questionObject.put("answers", answersArray)

                    questionsArray.put(questionObject)
                }

                topicObject.put("questions", questionsArray)
                jsonArray.put(topicObject)
            }

            val fileOutputStream: FileOutputStream = context.openFileOutput("questions.json", Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            outputStreamWriter.write(jsonArray.toString())
            outputStreamWriter.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}