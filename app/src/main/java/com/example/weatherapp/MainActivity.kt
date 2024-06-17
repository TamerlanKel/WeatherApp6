import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.text.DecimalFormat
import org.json.JSONObject
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private val API_KEY = "YOUR_API_KEY" // Замените на свой API ключ OpenWeatherMap
    private val cityName = "Санкт-Петербург"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshButton.setOnClickListener {
            updateData()
        }

        updateData()
    }

    private fun updateData() {
        val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY&units=metric")
        val connection = url.openConnection() as HttpsURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val dataString = inputStream.bufferedReader().use { it.readText() }
                val data = JSONObject(dataString)

                val main = data.getJSONObject("main")
                val currentTemp = main.getDouble("temp")
                val feelsLike = main.getDouble("feels_like")

                currentTempTextView.text = formatTemperature