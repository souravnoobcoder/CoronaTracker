package com.example.coronatracker.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import com.example.coronatracker.room.indiaStateModel
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.adapters.StateAdapter.ViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.coronatracker.R
import android.widget.TextView
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import android.view.animation.AnimationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class StateAdapter(private var stateModelList: List<indiaStateModel>?) :
    RecyclerView.Adapter<ViewHolder>() {
    private var expanded = false
    fun update(stateModelList: List<indiaStateModel>) {
        this.stateModelList = stateModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.for_state, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = stateModelList!![position]
        val confirmed = holder.confirmed
        val recovered = holder.recovered
        val deaths = holder.deaths
        val stateName = holder.stateName
        val helplineNumber = holder.helplineNumber
        val foreignConfirmed = holder.foreignConfirmed
        val activeCases = holder.activeCases
        val viewMore = holder.viewMore
        val moreDataLayout = holder.moreDataLayout
        val box = holder.box
        stateName.text = model.loc
        helplineNumber.text = model.number
        confirmed.text = model.totalConfirmed.toString()
        recovered.text = model.discharged.toString()
        deaths.text = model.deaths.toString()
        foreignConfirmed.text = model.confirmedCasesForeign.toString()
        activeCases.text = model.activeCases.toString()
           box.setOnClickListener {
               if (expanded) {
                   TransitionManager.beginDelayedTransition(box, AutoTransition())
                   moreDataLayout.visibility = View.GONE
                   viewMore.visibility = View.VISIBLE
                   expanded = false
               } else {
                   TransitionManager.beginDelayedTransition(box, AutoTransition())
                   viewMore.visibility = View.GONE
                   moreDataLayout.visibility = View.VISIBLE
                   expanded = true
               }
       }


        setLeftAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return if (stateModelList == null) 0 else stateModelList!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stateName: TextView = itemView.findViewById(R.id.locationNameText)
        var confirmed: TextView = itemView.findViewById(R.id.confirmedCaseText)
        var deaths: TextView = itemView.findViewById(R.id.deathsText)
        var recovered: TextView = itemView.findViewById(R.id.recoveredText)
        var activeCases: TextView = itemView.findViewById(R.id.indianCitizen_expandedCard)
        var helplineNumber: TextView = itemView.findViewById(R.id.helpline_expandedCard)
        var foreignConfirmed: TextView = itemView.findViewById(R.id.foreigner_expandedCard)
        var viewMore: TextView = itemView.findViewById(R.id.viewMore)
        var moreDataLayout: LinearLayout = itemView.findViewById(R.id.moreDataLay)
        var box: MaterialCardView = itemView.findViewById(R.id.stateDataCard)

    }

    companion object {
        private var lastPosition = -1
        fun setLeftAnimation(view: View, position: Int) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                val animation =
                    AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
                view.startAnimation(animation)
                lastPosition = position
            }
        }
    }
}