package com.example.coronatracker.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.databinding.ForStateBinding
import com.example.coronatracker.room.India

class StateAdapter : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    private var india: List<India> = emptyList()
    private var expanded = false
    fun update(updatedIndia: List<India>) {
        india = updatedIndia
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ForStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val state = india[position]
        holder.itemBinding.apply {
            locationName.text = state.loc
            helpline.text = state.number
            confirmedCase.text = state.totalConfirmed
            recovered.text = state.recovered
            deaths.text=state.deaths
            foreignerCases.text=state.confirmedCasesForeign
            activeCases.text=state.activeCases
            stateDataCard.setOnClickListener {
                if (expanded) {
                    TransitionManager.beginDelayedTransition(stateDataCard, AutoTransition())
                    moreDataLayout.visibility = View.GONE
                    viewMore.visibility = View.VISIBLE
                    expanded = false
                } else {
                    TransitionManager.beginDelayedTransition(stateDataCard, AutoTransition())
                    viewMore.visibility = View.GONE
                    moreDataLayout.visibility = View.VISIBLE
                    expanded = true
                }
            }
        }
        setLeftAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return india.size
    }

    class ViewHolder(var itemBinding: ForStateBinding) : RecyclerView.ViewHolder(itemBinding.root)

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