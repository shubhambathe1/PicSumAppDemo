package com.shubham.mediamagicassignment.networking

import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shubham.mediamagicassignment.MainActivity
import com.shubham.mediamagicassignment.adapter.PostAdapter
import com.shubham.mediamagicassignment.models.PostModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

class AsyncLibrary internal constructor(
    private var context: MainActivity,
    private var postItems: RecyclerView
) :
    AsyncTask<String, String, String?>() {

    private var resp: String? = null
    private val activityReference: WeakReference<MainActivity> = WeakReference(context)
    private val requestType = "GET"
    private val readTimeout = 15000
    private val connectTimeout = 15000

    private lateinit var httpURLConnection: HttpURLConnection
    private var inputLine: String? = null

    private var postList = ArrayList<PostModel>()

    override fun onPreExecute() {
        super.onPreExecute()
        // Log.e("TAG", "PRE")
        val activity = activityReference.get()
        if (activity == null || activity.isFinishing) return
        activity.progressBar.visibility = View.VISIBLE
    }

    override fun doInBackground(vararg params: String?): String? {
        // Log.e("TAG", "BACKGROUND")
        try {

            val url = URL(params[0])

            httpURLConnection = url.openConnection() as HttpURLConnection

            httpURLConnection.requestMethod = requestType
            httpURLConnection.readTimeout = readTimeout
            httpURLConnection.connectTimeout = connectTimeout

            // Connect to our url
            httpURLConnection.connect()

            // Create a new InputStreamReader
            val inputStreamReader = InputStreamReader(httpURLConnection.inputStream)

            // Create a new BufferedReader and StringBuilder
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()

            // Check if the line we are reading is not null
            inputLine = bufferedReader.readLine()
            while (inputLine != null) {
                stringBuilder.append(inputLine)
                inputLine = bufferedReader.readLine()
            }

            // Close out InputStream and BufferedReader
            bufferedReader.close()
            inputStreamReader.close()

            // Set our result equla to our stringBuilder
            resp = stringBuilder.toString()

        } catch (e: InterruptedException) {
            e.printStackTrace()
            e.message
        } catch (e: Exception) {
            e.printStackTrace()
            e.message
        }

        return resp
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // Log.e("TAG", "POST")
        Log.e("TAG", result)

        val activity = activityReference.get()

        if (activity == null || activity.isFinishing) return

        activity.progressBar.visibility = View.GONE

        val jsonArray = JSONArray(result)
        var jsonObject = JSONObject()

        for (i in 0 until jsonArray.length()) {

            jsonObject = JSONObject(jsonArray[i].toString())

            // Mapping JSON keys to local variables
            val authorName = jsonObject.getString("author")
            val authorUrl = jsonObject.getString("author_url")
            val fileName = jsonObject.getString("filename")
            val format = jsonObject.getString("format")
            val height = jsonObject.getString("height")
            val id = jsonObject.getString("id")
            val postUrl = jsonObject.getString("post_url")
            val width = jsonObject.getString("width")

            // Creating a PostModel Object
            val postModel =
                PostModel(
                    authorName,
                    authorUrl,
                    fileName,
                    format,
                    height,
                    id,
                    postUrl,
                    width
                )

            // Adding PostModel Object to our list
            postList.add(postModel)
        }

        // Log.e("TAG", postList.toString())

        val gridLayoutManager = GridLayoutManager(context, 2)

        postItems.layoutManager = gridLayoutManager
        val postAdapter = PostAdapter(postList, context)

        postItems.adapter = postAdapter

        postAdapter.notifyDataSetChanged()

        // Log.e("TAG", jsonObject.getString("author"))
    }

    override fun onProgressUpdate(vararg values: String?) {
        val activity = activityReference.get()
        if (activity == null || activity.isFinishing) return

        Toast.makeText(activity, values.firstOrNull(), Toast.LENGTH_SHORT).show()
    }
}