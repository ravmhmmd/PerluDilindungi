package com.example.perludilindungi.news


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.perludilindungi.R
import com.example.perludilindungi.data.ResultsFaskes
import com.example.perludilindungi.data.ResultsNews
import java.io.InputStream

class CustomAdapter internal constructor(internal var context: Context, private val mList: List<ResultsNews>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class

        // sets the text to the textview from our itemHolder class
        holder.textViewTitle.text = ItemsViewModel.title
        holder.textViewDate.text = ItemsViewModel.pubDate

        Glide.with(context)
            .load(ItemsViewModel.enclosure!!.Url)
            .into(holder.newsThumbnail);

        val action = NewsFragmentDirections.openNewsDetail(
            if (ItemsViewModel.title != null) ItemsViewModel.title!! else "",
            ItemsViewModel.link[0],
            if (ItemsViewModel.guid != null) ItemsViewModel.guid!! else "",
            if (ItemsViewModel.pubDate != null) ItemsViewModel.pubDate!! else "",
            if (ItemsViewModel.description != null) ItemsViewModel.description!!._cdata!! else "",
            if (ItemsViewModel.enclosure != null) ItemsViewModel.enclosure!!.Url!! else "",
        )

        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(action)
        )
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val newsThumbnail: ImageView = itemView.findViewById(R.id.newsThumbnail)
    }
}