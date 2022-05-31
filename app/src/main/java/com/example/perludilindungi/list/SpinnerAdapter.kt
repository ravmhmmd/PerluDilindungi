package com.example.perludilindungi.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.perludilindungi.R

class SpinnerAdapter internal constructor(internal var context: Context, private var list: MutableList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): String {
        return list[i];
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        var view = view

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.spinner_item_closed, viewGroup, false)
        }

        val textView = view!!.findViewById<TextView>(R.id.textViewSpinnerClosed)
        textView.text = list[i]
        return textView

    }

    override fun getDropDownView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.spinner_item, viewGroup, false)
        }

        val textView = view!!.findViewById<TextView>(R.id.textViewSpinner)
        textView.text = list[i]
        return textView
    }

}