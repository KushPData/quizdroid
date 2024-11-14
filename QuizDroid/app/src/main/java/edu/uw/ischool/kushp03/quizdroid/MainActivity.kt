package edu.uw.ischool.kushp03.quizdroid

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.provider.Settings
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var goToPreferences: Button
    private lateinit var topicRepository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        topicRepository = (application as QuizApp).getTopicRepository()

        getTopicListFragment()

        if (deviceIsConnected()) {
            downloadData()
        } else {
            askForUserInputRelatedToNetwork()
        }

        goToPreferences = findViewById(R.id.goToPreferences)

        goToPreferences.setOnClickListener {
            val intent = Intent(this, Preferences::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        topicRepository = (application as QuizApp).getTopicRepository()

        downloadData()

        getTopicListFragment()
    }

    private fun deviceIsConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun downloadData() {
        val sharedPreference = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val url = sharedPreference.getString("newUrl", "http://tednewardsandbox.site44.com/questions.json")
        (topicRepository as TopicRepositoryImplementation).apply {
            setDownLoadStatusListener(object : TopicRepositoryImplementation.DownloadStatusListener {
                override fun downloadStatus(wasSuccessful: Boolean, message: String) {
                    runOnUiThread {
                        if(wasSuccessful) {
                            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                        } else {
                            askForUserInputRelatedToFailedDownload(message)

                        }
                    }
                }
            })
            fetch(url)
        }
    }

    private fun askForUserInputRelatedToFailedDownload(message: String) {
        val downloadNotification = AlertDialog.Builder(this)

        downloadNotification.setTitle("Could not download data")
        downloadNotification.setMessage("$message. Do you want to try downloading again?")
            .setPositiveButton("Retry") { _, _ -> downloadData()}
            .setNegativeButton("Quit App") { _, _ -> finish()}
    }

    private fun askForUserInputRelatedToNetwork() {
        val networkNotification = AlertDialog.Builder(this)

        networkNotification.setTitle("Could not connect to the internet")

        val checkIfAirplaneModeOn = Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0

        if(checkIfAirplaneModeOn) {
            networkNotification.setMessage("Airplane mode detected. Do you want to turn it off?")
                .setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))}
                .setNegativeButton("Close") {dialog, _ -> dialog.dismiss()}
        } else {
            networkNotification.setMessage("No signal detected")
                .setPositiveButton("Retry") {_, _ ->
                    if(deviceIsConnected()) {
                        downloadData()
                    } else {
                        askForUserInputRelatedToNetwork()
                    }
                }
                .setNegativeButton("Quit") {_, _ -> finish()}
        }
        networkNotification.show()
    }

    private fun getTopicListFragment() {
        val topicListFragment = TopicList()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, topicListFragment).commit()
    }
}
