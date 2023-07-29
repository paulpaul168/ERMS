package at.paulhoeller.erms.presentation
import android.os.AsyncTask
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HttpPostTask(private val url: String, private val messageData: Any) : AsyncTask<Unit, Unit, String>() {

    override fun doInBackground(vararg params: Unit?): String {
        val url = URL(url)
        val jsonPayload = Gson().toJson(messageData).toString()
        println(jsonPayload)
        with(url.openConnection() as HttpsURLConnection) {
            requestMethod = "POST"
            doOutput = true
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")

            val outputStream: OutputStream = outputStream
            outputStream.write(jsonPayload.toByteArray())

            val responseCode = responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()

                var inputLine: String?
                while (bufferedReader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                bufferedReader.close()

                return response.toString()
            } else {
                return "Error: Response Code $responseCode"
            }
        }
    }

    override fun onPostExecute(result: String?) {
        // Handle the result here, update UI, etc.
        println(result)
    }
}

// To use the AsyncTask, execute it like this:
// HttpPostTask("https://en3gu2b6yxhqr.x.pipedream.net", messageData).execute()
