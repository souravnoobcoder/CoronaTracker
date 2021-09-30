package com.example.coronatracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.R
import com.example.coronatracker.adapters.CountryHistoryAdapter.HistoryViewHolder
import com.example.coronatracker.room.CountryRecent

class CountryHistoryAdapter(private var recentList: List<CountryRecent>) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
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