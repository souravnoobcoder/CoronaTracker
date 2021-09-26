package com.example.coronatracker.Adapters;


import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.dataClasses.CountryInfo;
import com.example.coronatracker.dataClasses.Root;
import com.example.coronatracker.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;



public class countryAdapter extends RecyclerView.Adapter<countryAdapter.viewHold> {
    boolean expanded=false;
    List<Root> list;
    private static int lastPosition=-1;

    public void update(List<Root> updatedRootList){
        this.list=updatedRootList;
        notifyDataSetChanged();
    }
    public countryAdapter(List<Root> list) {
        this.list = list;
    }

    String[] countries={"Anguilla","Antigua and Barbuda","Aruba","Bahamas","Barbados","Belize","Bermuda","British Virgin Islands","Canada",
            "Caribbean Netherlands","Cayman Islands","Costa Rica","Cuba","Curaçao","Dominica","Dominican Republic","El Salvador","Greenland","Grenada",
            "Guadeloupe","Guatemala","Haiti","Honduras","Jamaica","Martinique","Mexico","Montserrat","Nicaragua","Panama","Saint Kitts and Nevis",
            "Saint Lucia","Saint Martin","Saint Pierre Miquelon","Saint Vincent and the Grenadines","Sint Maarten","St. Barth","Trinidad and Tobago",
            "Turks and Caicos Islands","USA","Afghanistan","Armenia","Azerbaijan","Bahrain","Bangladesh","Bhutan","Brunei","Cambodia","China",
            "Cyprus","Georgia","Hong Kong","India","Indonesia","Iran","Iraq","Israel","Japan","Jordan","Kazakhstan","Kuwait","Kyrgyzstan",
            "Lao People's Democratic Republic","Lebanon","Macao","Malaysia","Maldives","Mongolia","Myanmar","Nepal","Oman","Pakistan",
            "Palestine","Philippines","Qatar","S. Korea","Saudi Arabia","Singapore","Sri Lanka","Syrian Arab Republic","Taiwan","Tajikistan",
            "Thailand","Timor-Leste","Turkey","UAE","Uzbekistan","Vietnam","Yemen","Argentina","Bolivia","Brazil","Chile","Colombia","Ecuador",
            "Falkland Islands (Malvinas)","French Guiana","Guyana","Paraguay","Peru","Suriname","Uruguay","Venezuela","Albania","Andorra","Austria",
            "Belarus","Belgium","Bosnia","Bulgaria","Channel Islands","Croatia","Czechia","Denmark","Estonia","Faroe Islands","Finland","France",
            "Germany","Gibraltar","Greece","Holy See (Vatican City State)","Hungary","Iceland","Ireland","Isle of Man","Italy","Latvia","Liechtenstein",
            "Lithuania","Luxembourg","Macedonia","Malta","Moldova","Monaco","Montenegro","Netherlands","Norway","Poland","Portugal","Romania","Russia",
            "San Marino","Serbia","Slovakia","Slovenia","Spain","Sweden","Switzerland","UK","Ukraine","Algeria","Angola","Benin","Botswana","Burkina Faso",
            "Burundi","Cabo Verde","Cameroon","Central African Republic","Chad","Comoros","Congo","Côte d'Ivoire","DRC","Djibouti","Egypt","Equatorial Guinea",
            "Eritrea","Ethiopia","Gabon","Gambia","Ghana","Guinea","Guinea-Bissau","Kenya","Lesotho","Liberia","Libyan Arab Jamahiriya","Madagascar","Malawi",
            "Mali","Mauritania","Mauritius","Mayotte","Morocco","Mozambique","Namibia","Niger","Nigeria","Rwanda","Réunion","Saint Helena","Sao Tome and Principe",
            "Senegal","Seychelles","Sierra Leone","Somalia","South Africa","South Sudan","Sudan","Swaziland","Tanzania","Togo","Tunisia","Uganda","Western Sahara",
            "Zambia","Zimbabwe","Australia","Fiji","French Polynesia","Marshall Islands","Micronesia","New Caledonia","New Zealand","Palau","Papua New Guinea",
            "Samoa","Solomon Islands","Vanuatu","Wallis and Futuna"};
    @NonNull
    @Override
    public viewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.corona_list_item,parent,false);
        return new viewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHold holder, int position) {
        Root data=list.get(position);
        TextView country= holder.country;
        TextView totalPopulation= holder.totalPopulation;
        TextView confirmed= holder.confirmed;
        TextView recovered= holder.recovered;
        TextView deaths= holder.deaths;
        TextView casesToday= holder.casesToday;
        TextView activeCases= holder.activeCases;
        TextView deathsToday= holder.deathsToday;
        TextView criticalCases= holder.criticalCases;
        TextView casesPerMillion= holder.casesPerMillion;
        TextView deathsPerMillion= holder.deathsPerMillion;
        TextView viewMore= holder.viewMore;
        LinearLayout moreDataLayout= holder.moreDataLayout;
        MaterialCardView box = holder.box;
        ImageView countryFlag = holder.countryFlag;

        country.setText(String.valueOf(data.country));
        totalPopulation.setText(String.valueOf(data.population));
        confirmed.setText(String.valueOf(data.cases));
        recovered.setText(String.valueOf(data.recovered));
        deaths.setText(String.valueOf(data.deaths));
        casesToday.setText(String.valueOf(data.todayCases));
        activeCases.setText(String.valueOf(data.active));
        deathsToday.setText(String.valueOf(data.todayDeaths));
        criticalCases.setText(String.valueOf(data.critical));
        casesPerMillion.setText(String.valueOf(data.casesPerOneMillion));
        deathsPerMillion.setText(String.valueOf(data.deathsPerOneMillion));
        CountryInfo info= data.countryInfo;
        if (info!=null)
        Picasso.get().load(info.flag).noFade().resize(50, 22).into(countryFlag);

        new Handler().post(() -> box.setOnClickListener(v -> {

            if (expanded) {
                TransitionManager.beginDelayedTransition(box, new AutoTransition());
                moreDataLayout.setVisibility(View.GONE);
                viewMore.setVisibility(View.VISIBLE);
                expanded = false;
            } else {
                TransitionManager.beginDelayedTransition(box, new AutoTransition());
                viewMore.setVisibility(View.GONE);
                moreDataLayout.setVisibility(View.VISIBLE);
                expanded = true;
            }
        }));
        setLeftAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class viewHold extends RecyclerView.ViewHolder {
        TextView country,totalPopulation,confirmed,recovered,deaths,
                casesToday,activeCases,deathsToday,criticalCases,casesPerMillion
                ,deathsPerMillion, viewMore;
        LinearLayout moreDataLayout;
        MaterialCardView box;
        ImageView countryFlag;
        public viewHold(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.locationNameTextView);
            totalPopulation=itemView.findViewById(R.id.total_populationNumber);
            confirmed=itemView.findViewById(R.id.confirmedCaseTextView);
            recovered=itemView.findViewById(R.id.recoveredTextView);
            deaths=itemView.findViewById(R.id.deathsTextView);
            casesToday=itemView.findViewById(R.id.casesToday_expandedCard);
            activeCases=itemView.findViewById(R.id.activeCases_expandedCard);
            deathsToday=itemView.findViewById(R.id.deathsToday_expandedCard);
            criticalCases=itemView.findViewById(R.id.criticalCases_expandedCard);
            casesPerMillion=itemView.findViewById(R.id.casesPerMillion_expandedCard);
            deathsPerMillion=itemView.findViewById(R.id.deathsPerMillion_expandedCard);
            viewMore=itemView.findViewById(R.id.viewMoreText);
            moreDataLayout=itemView.findViewById(R.id.moreDataLayout);
            box = itemView.findViewById(R.id.globalDataCard);
            countryFlag = itemView.findViewById(R.id.country_flag);
        }
    }
    public static void setLeftAnimation(View view, int position){
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }
}
