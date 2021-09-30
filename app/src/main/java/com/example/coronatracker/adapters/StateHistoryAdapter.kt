package com.example.coronatracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.R
import com.example.coronatracker.adapters.StateHistoryAdapter.*
import com.example.coronatracker.room.StateRecent

class StateHistoryAdapter(private var recentList: List<StateRecent>) : RecyclerView.Adapter<HistoryViewHolder>(){
    fun update(recentList: List<StateRecent>){
        this.recentList=recentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item=holder.item
        item.text=recentList[position].name
    }

    override fun getItemCount(): Int {
        return recentList.size
    }
    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var item: TextView =itemView.findViewById(R.id.history_item)
    }
}