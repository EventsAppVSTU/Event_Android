package com.example.eventsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterForWeekList extends RecyclerView.Adapter<AdapterForWeekList.WeekListViewHolder> {


    private int count;

    public AdapterForWeekList(int count){
        this.count = count;

    }

    @NonNull
    @Override
    public AdapterForWeekList.WeekListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.week_element;


        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        AdapterForWeekList.WeekListViewHolder viewHolder = new AdapterForWeekList.WeekListViewHolder(view);





        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForWeekList.WeekListViewHolder holder, int position) {
        holder.bind(position);
        final TextView day = holder.itemView.findViewById(R.id.day);
        final TextView month = holder.itemView.findViewById(R.id.month);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                day.setTextColor(Color.parseColor("#FF004D"));
                month.setTextColor(Color.parseColor("#FF004D"));
            }
        });


    }

    @Override
    public int getItemCount() {
        return count;
    }

    class WeekListViewHolder extends RecyclerView.ViewHolder {



        public WeekListViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        void bind(int Index){

        }
    }

}
