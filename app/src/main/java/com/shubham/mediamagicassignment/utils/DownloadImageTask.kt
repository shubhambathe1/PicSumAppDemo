package com.shubham.mediamagicassignment.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.net.URL

class DownloadImageTask(var postImage: ImageView) :
    AsyncTask<String?, Void?, Bitmap?>() {

    override fun doInBackground(vararg urls: String?): Bitmap? {
        val displayUrl = urls[0]
        var bmp: Bitmap? = null
        try {
            val `in`: InputStream = URL(displayUrl).openStream()
            bmp = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("Error", e.message!!)
            e.printStackTrace()
        }
        return bmp
    }

    override fun onPostExecute(result: Bitmap?) {
        postImage.setImageBitmap(result)
    }
}