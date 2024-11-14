package edu.uw.ischool.kushp03.quizdroid

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.Executors
import java.net.URL

class TopicRepositoryImplementation(private val context: Context) : TopicRepository {
//    private val hardcodedTopics = arrayListOf<TopicModel>(
//        TopicModel("Science!", "Because SCIENCE!", "Because SCIENCE!", listOf<QuestionModel>(
//            QuestionModel("What is fire?", listOf<String>("One of the four classical elements", "A magical reaction given to us by God", "A band that hasn't yet been discovered", "Fire! Fire! Fire! heh-heh"), 1)
//        )),
//        TopicModel("Marvel Super Heroes", "Avengers, Assemble!", "Avengers, Assemble!", listOf<QuestionModel>(
//            QuestionModel("Who is Iron Man?", listOf<String>("Tony Stark", "Obadiah Stane", "A rock hit by Megadeth", "Nobody knows"), 1),
//            QuestionModel("Who founded the X-men?", listOf<String>("Tony Stark", "Professor X", "The X-Institute", "Erik Lensherr"), 2),
//            QuestionModel("How did Spider-Man get his powers?", listOf<String>("He was bitten by a radioactive spider", "He ate a radioactive spider", "He is a radioactive spider", "He looked at a radioactive spider"), 1)
//        )),
//        TopicModel("Mathematics", "Did you pass the third grade?", "Did you pass the third grade?", listOf<QuestionModel>(
//            QuestionModel("What is 2+2?", listOf<String>("4", "22", "An irrational number", "Nobody knows"), 1)
//        ))
//    )

    private val executor = Executors.newFixedThreadPool(5)
    private var downloadStatusListener: DownloadStatusListener? = null

    interface DownloadStatusListener {
        fun downloadStatus(wasSuccessful: Boolean, message: String)
    }

    fun setDownLoadStatusListener(listener: DownloadStatusListener) {
        downloadStatusListener = listener
    }

    fun fetch(url: String?) {
//        val url = "http://tednewardsandbox.site44.com/questions.json"

        Toast.makeText(context, "Downloading from $url", Toast.LENGTH_SHORT).show()

        executor.execute {
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                val responseStream = ByteArrayOutputStream()

                try {
                    val incoming = BufferedInputStream(urlConnection.inputStream).bufferedReader()
                    Log.i("TopicRepositoryImplementation", "got the incoming buffered stream")
                    incoming.forEachLine {
                        Log.i("TopicRepositoryImplementation", "it.toByteArray()${it.toByteArray()}")
                        responseStream.write(it.toByteArray())
                    }
                } finally {
                    urlConnection.disconnect()
                }

                val data = responseStream.toString()

                if (data.isNotEmpty()) {
                    val fileOutputStream: FileOutputStream = context.openFileOutput("questions.json", Context.MODE_PRIVATE)
                    val outputStreamWriter = OutputStreamWriter(fileOutputStream)
                    outputStreamWriter.write(data)
                    outputStreamWriter.close()

                    downloadStatusListener?.downloadStatus(true, "Download Successful")
                } else {
                    downloadStatusListener?.downloadStatus(false, "Empty Response")
                }
            }
            catch (e: IOException) {
                downloadStatusListener?.downloadStatus(false, "Download Failed with error: ${e.message}")
            }

        }
    }

    override fun getTopics() : ArrayList<TopicModel> {

        val topics = arrayListOf<TopicModel>()

        try {
            val fileInputStream: FileInputStream = context.openFileInput("questions.json")
            val jsonString = InputStreamReader(fileInputStream).readText()
            val jsonArray = JSONArray(jsonString)

            for(i in 0 until jsonArray.length()) {
                val topicObject = jsonArray.getJSONObject(i)
                val title = topicObject.getString("title")
                Toast.makeText(context, "Title: $title", Toast.LENGTH_SHORT).show()
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

//    private fun addTopicsToFile(topics: List<TopicModel>) {
//        try {
//            val jsonArray = JSONArray()
//
//            for (topic in topics) {
//                val topicObject = JSONObject()
//                topicObject.put("title", topic.topicName)
////                Once again, using short description and long description with the same value
//                topicObject.put("desc", topic.topicShortDescription)
//
//                val questionsArray = JSONArray()
//
//                for(question in topic.questions) {
//                    val questionObject = JSONObject()
//
//                    questionObject.put("text", question.question)
//                    questionObject.put("answer", question.correctAnswer.toString())
//
//                    val answersArray = JSONArray()
//
//                    for(answer in question.answerChoices) {
//                        answersArray.put(answer)
//                    }
//
//                    questionObject.put("answers", answersArray)
//
//                    questionsArray.put(questionObject)
//                }
//
//                topicObject.put("questions", questionsArray)
//                jsonArray.put(topicObject)
//            }
//
//            val fileOutputStream: FileOutputStream = context.openFileOutput("questions.json", Context.MODE_PRIVATE)
//            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
//            outputStreamWriter.write(jsonArray.toString())
//            outputStreamWriter.close()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
}