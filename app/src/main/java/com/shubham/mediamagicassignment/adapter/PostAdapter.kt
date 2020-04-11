package com.shubham.mediamagicassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.mediamagicassignment.R
import com.shubham.mediamagicassignment.models.PostModel
import com.shubham.mediamagicassignment.utils.DownloadImageTask
import kotlinx.android.synthetic.main.row_post_item.view.*

class PostAdapter(private val items: ArrayList<PostModel>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_post_item, parent, false)
        )
    }

    // Gets the number of Post in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val authorImageUrl = "https://picsum.photos/300/300?image="

        val postModel = items[position]

        val downloadImageTask = DownloadImageTask(holder.imageViewAuthor)
        downloadImageTask.execute(authorImageUrl + postModel.id)
        holder.textViewAuthorName.text = postModel.author
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Holds the Components from XML
    val imageViewAuthor: ImageView = view.imageViewAuthor
    val textViewAuthorName: TextView = view.textViewAuthorName
}