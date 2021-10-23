package com.example.coronatracker.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.databinding.CountryItemBinding
import com.example.coronatracker.room.Country
import com.squareup.picasso.Picasso

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHold>() {
    private var expanded = false
    private var countries: List<Country> = emptyList()
    fun update(updatedCountries: List<Country>) {
        countries = updatedCountries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHold(binding)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        val country = countries[position]
        holder.itemBinding.apply {
            locationName.text = country.country
            confirmedCase.text = country.confirmed
            recovered.text = country.recovered
            deaths.text = country.deaths
            casesToday.text = country.casesToday
            activeCases.text = country.activeCases
            deathsToday.text = country.deathsToday
            criticalCases.text = country.criticalCases
            casesPerMillion.text = country.casesPerMillion
            deathsPerMillion.text = country.deathsPerMillion
                Picasso.get().load(country.flagUrl)
                    .noFade().resize(50, 22).into(countryFlag)

            countryDataCard.setOnClickListener {
                if (expanded) {
                    TransitionManager.beginDelayedTransition(countryDataCard, AutoTransition())
                    moreDataLayout.visibility = GONE
                    viewMore.visibility = VISIBLE
                    expanded = false
                } else {
                    TransitionManager.beginDelayedTransition(countryDataCard, AutoTransition())
                    viewMore.visibility = GONE
                    moreDataLayout.visibility = VISIBLE
                    expanded = true
                }
            }
        }
        setLeftAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class ViewHold(var itemBinding: CountryItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

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