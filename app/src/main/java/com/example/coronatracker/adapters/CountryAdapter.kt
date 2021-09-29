package com.example.coronatracker.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.adapters.CountryAdapter.ViewHold
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.coronatracker.R
import android.widget.TextView
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.coronatracker.dataClasses.Root
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class CountryAdapter(var list: List<Root>?) : RecyclerView.Adapter<ViewHold>() {
    private var expanded = false
    fun update(updatedRootList: List<Root>?) {
        list = updatedRootList
        notifyDataSetChanged()
    }

    var countries = arrayOf(
        "Anguilla",
        "Antigua and Barbuda",
        "Aruba",
        "Bahamas",
        "Barbados",
        "Belize",
        "Bermuda",
        "British Virgin Islands",
        "Canada",
        "Caribbean Netherlands",
        "Cayman Islands",
        "Costa Rica",
        "Cuba",
        "Curaçao",
        "Dominica",
        "Dominican Republic",
        "El Salvador",
        "Greenland",
        "Grenada",
        "Guadeloupe",
        "Guatemala",
        "Haiti",
        "Honduras",
        "Jamaica",
        "Martinique",
        "Mexico",
        "Montserrat",
        "Nicaragua",
        "Panama",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Martin",
        "Saint Pierre Miquelon",
        "Saint Vincent and the Grenadines",
        "Sint Maarten",
        "St. Barth",
        "Trinidad and Tobago",
        "Turks and Caicos Islands",
        "USA",
        "Afghanistan",
        "Armenia",
        "Azerbaijan",
        "Bahrain",
        "Bangladesh",
        "Bhutan",
        "Brunei",
        "Cambodia",
        "China",
        "Cyprus",
        "Georgia",
        "Hong Kong",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Israel",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kuwait",
        "Kyrgyzstan",
        "Lao People's Democratic Republic",
        "Lebanon",
        "Macao",
        "Malaysia",
        "Maldives",
        "Mongolia",
        "Myanmar",
        "Nepal",
        "Oman",
        "Pakistan",
        "Palestine",
        "Philippines",
        "Qatar",
        "S. Korea",
        "Saudi Arabia",
        "Singapore",
        "Sri Lanka",
        "Syrian Arab Republic",
        "Taiwan",
        "Tajikistan",
        "Thailand",
        "Timor-Leste",
        "Turkey",
        "UAE",
        "Uzbekistan",
        "Vietnam",
        "Yemen",
        "Argentina",
        "Bolivia",
        "Brazil",
        "Chile",
        "Colombia",
        "Ecuador",
        "Falkland Islands (Malvinas)",
        "French Guiana",
        "Guyana",
        "Paraguay",
        "Peru",
        "Suriname",
        "Uruguay",
        "Venezuela",
        "Albania",
        "Andorra",
        "Austria",
        "Belarus",
        "Belgium",
        "Bosnia",
        "Bulgaria",
        "Channel Islands",
        "Croatia",
        "Czechia",
        "Denmark",
        "Estonia",
        "Faroe Islands",
        "Finland",
        "France",
        "Germany",
        "Gibraltar",
        "Greece",
        "Holy See (Vatican City State)",
        "Hungary",
        "Iceland",
        "Ireland",
        "Isle of Man",
        "Italy",
        "Latvia",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macedonia",
        "Malta",
        "Moldova",
        "Monaco",
        "Montenegro",
        "Netherlands",
        "Norway",
        "Poland",
        "Portugal",
        "Romania",
        "Russia",
        "San Marino",
        "Serbia",
        "Slovakia",
        "Slovenia",
        "Spain",
        "Sweden",
        "Switzerland",
        "UK",
        "Ukraine",
        "Algeria",
        "Angola",
        "Benin",
        "Botswana",
        "Burkina Faso",
        "Burundi",
        "Cabo Verde",
        "Cameroon",
        "Central African Republic",
        "Chad",
        "Comoros",
        "Congo",
        "Côte d'Ivoire",
        "DRC",
        "Djibouti",
        "Egypt",
        "Equatorial Guinea",
        "Eritrea",
        "Ethiopia",
        "Gabon",
        "Gambia",
        "Ghana",
        "Guinea",
        "Guinea-Bissau",
        "Kenya",
        "Lesotho",
        "Liberia",
        "Libyan Arab Jamahiriya",
        "Madagascar",
        "Malawi",
        "Mali",
        "Mauritania",
        "Mauritius",
        "Mayotte",
        "Morocco",
        "Mozambique",
        "Namibia",
        "Niger",
        "Nigeria",
        "Rwanda",
        "Réunion",
        "Saint Helena",
        "Sao Tome and Principe",
        "Senegal",
        "Seychelles",
        "Sierra Leone",
        "Somalia",
        "South Africa",
        "South Sudan",
        "Sudan",
        "Swaziland",
        "Tanzania",
        "Togo",
        "Tunisia",
        "Uganda",
        "Western Sahara",
        "Zambia",
        "Zimbabwe",
        "Australia",
        "Fiji",
        "French Polynesia",
        "Marshall Islands",
        "Micronesia",
        "New Caledonia",
        "New Zealand",
        "Palau",
        "Papua New Guinea",
        "Samoa",
        "Solomon Islands",
        "Vanuatu",
        "Wallis and Futuna"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.corona_list_item, parent, false)
        return ViewHold(view)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        val data = list!![position]
        val country = holder.country
        val totalPopulation = holder.totalPopulation
        val confirmed = holder.confirmed
        val recovered = holder.recovered
        val deaths = holder.deaths
        val casesToday = holder.casesToday
        val activeCases = holder.activeCases
        val deathsToday = holder.deathsToday
        val criticalCases = holder.criticalCases
        val casesPerMillion = holder.casesPerMillion
        val deathsPerMillion = holder.deathsPerMillion
        val viewMore = holder.viewMore
        val moreDataLayout = holder.moreDataLayout
        val box = holder.box
        val countryFlag = holder.countryFlag
        country.text = data.country.toString()
        totalPopulation.text = data.population.toString()
        confirmed.text = data.cases.toString()
        recovered.text = data.recovered.toString()
        deaths.text = data.deaths.toString()
        casesToday.text = data.todayCases.toString()
        activeCases.text = data.active.toString()
        deathsToday.text = data.todayDeaths.toString()
        criticalCases.text = data.critical.toString()
        casesPerMillion.text = data.casesPerOneMillion.toString()
        deathsPerMillion.text = data.deathsPerOneMillion.toString()
        val info = data.countryInfo
        if (info != null) Picasso.get().load(info.flag).noFade().resize(50, 22).into(countryFlag)
       CoroutineScope(Main).launch {
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
       }
        setLeftAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

    inner class ViewHold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var country: TextView = itemView.findViewById(R.id.locationNameTextView)
        var totalPopulation: TextView = itemView.findViewById(R.id.total_populationNumber)
        var confirmed: TextView = itemView.findViewById(R.id.confirmedCaseTextView)
        var recovered: TextView = itemView.findViewById(R.id.recoveredTextView)
        var deaths: TextView = itemView.findViewById(R.id.deathsTextView)
        var casesToday: TextView = itemView.findViewById(R.id.casesToday_expandedCard)
        var activeCases: TextView = itemView.findViewById(R.id.activeCases_expandedCard)
        var deathsToday: TextView = itemView.findViewById(R.id.deathsToday_expandedCard)
        var criticalCases: TextView = itemView.findViewById(R.id.criticalCases_expandedCard)
        var casesPerMillion: TextView = itemView.findViewById(R.id.casesPerMillion_expandedCard)
        var deathsPerMillion: TextView = itemView.findViewById(R.id.deathsPerMillion_expandedCard)
        var viewMore: TextView = itemView.findViewById(R.id.viewMoreText)
        var moreDataLayout: LinearLayout = itemView.findViewById(R.id.moreDataLayout)
        var box: MaterialCardView = itemView.findViewById(R.id.globalDataCard)
        var countryFlag: ImageView = itemView.findViewById(R.id.country_flag)
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