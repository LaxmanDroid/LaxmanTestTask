package com.example.testassignmentapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapters(private var list: List<String>, private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapters.MyView>() {

    inner class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var profile_username: TextView

        init {
            profile_username = view.findViewById<View>(R.id.title) as TextView

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)
        return MyView(itemView)
    }

    override fun onBindViewHolder(
        holder: MyView,
        position: Int
    ) {
        holder.profile_username.text = list[position]

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(mList: List<String>) {
        list = mList
        notifyDataSetChanged()
    }

}