package com.shubham.mediamagicassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shubham.mediamagicassignment.networking.AsyncLibrary
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val asyncLibrary = AsyncLibrary(this, post_items)
        asyncLibrary.execute("https://picsum.photos/list")
    }
}
