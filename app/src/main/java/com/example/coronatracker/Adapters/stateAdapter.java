package com.example.coronatracker.Adapters;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.coronatracker.DataClasses.indiaStateModel;
import com.example.coronatracker.DataClasses.indianStates;
import com.example.coronatracker.DataClasses.stateContacts;
import com.example.coronatracker.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class stateAdapter extends RecyclerView.Adapter<stateAdapter.viewHolder> {
    boolean expanded=false;
    List<indiaStateModel> stateModelList;
    public stateAdapter(List<indiaStateModel> stateModelList){
        this.stateModelList=stateModelList;
    }
    public void update(List<indiaStateModel> stateModelList){
        this.stateModelList=stateModelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public stateAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.for_state,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull stateAdapter.viewHolder holder, int position) {
        indiaStateModel model=stateModelList.get(position);
        TextView confirmed= holder.confirmed;
        TextView recovered= holder.recovered;
        TextView deaths= holder.deaths;
        TextView stateName=holder.stateName;
        TextView helplineNumber= holder.helplineNumber;
        TextView foreignConfirmed=holder.foreignConfirmed;
        TextView viewMore=holder.viewMore;
        LinearLayout moreDataLayout= holder.moreDataLayout;
        MaterialCardView box = holder.box;


        stateName.setText(model.getLoc());
        helplineNumber.setText(model.getNumber());
        confirmed.setText(String.valueOf(model.getTotalConfirmed()));
        recovered.setText(String.valueOf(model.getDischarged()));
        deaths.setText(String.valueOf(model.getDeaths()));
        foreignConfirmed.setText(String.valueOf(model.getConfirmedCasesForeign()));

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateModelList ==null ? 0 : stateModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView stateName,confirmed,deaths,recovered,activeCases,helplineNumber,
                foreignConfirmed,viewMore;
        LinearLayout moreDataLayout;
        MaterialCardView box;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            confirmed=itemView.findViewById(R.id.confirmedCaseTextView);
            recovered=itemView.findViewById(R.id.recoveredTextView);
            deaths=itemView.findViewById(R.id.deathsTextView);
            stateName=itemView.findViewById(R.id.locationNameTextView);
            activeCases=itemView.findViewById(R.id.indianCitizen_expandedCard);
            helplineNumber=itemView.findViewById(R.id.helpline_expandedCard);
            foreignConfirmed=itemView.findViewById(R.id.foreigner_expandedCard);
            moreDataLayout=itemView.findViewById(R.id.moreDataLayout);
            box=itemView.findViewById(R.id.stateDataCard);
            viewMore=itemView.findViewById(R.id.viewMoreText);
        }
    }
}
