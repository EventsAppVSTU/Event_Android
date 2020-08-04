package com.example.eventsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;

import java.security.AccessControlContext;
import java.util.List;
import java.util.Random;

import static java.security.AccessController.getContext;

public class AdapterForScheduleCard extends RecyclerView.Adapter<AdapterForScheduleCard.ScheduleViewHolder> {

    private List<Object> data;
    private Context context;

    public AdapterForScheduleCard(List<Object> Data, Context Context){
        this.data = Data;
        this.context = Context;

    }

    @NonNull
    @Override
    public AdapterForScheduleCard.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.time_table_element;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        AdapterForScheduleCard.ScheduleViewHolder viewHolder = new AdapterForScheduleCard.ScheduleViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForScheduleCard.ScheduleViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView performanceName;
        TextView speaker;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.timeStart);
            endTime = itemView.findViewById(R.id.timeEnd);
            performanceName = itemView.findViewById(R.id.performanceName);
            speaker = itemView.findViewById(R.id.speakerName);
        }
        void bind(int Index){
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) data.get(Index);
            String startTimeResult = "";
            String[] sTime = linkedTreeMap.get("startTime").toString().split(":");
            startTimeResult = sTime[0]+":"+sTime[1];
            startTime.setText(startTimeResult);

            String endTimeResult = "";
            String[] eTime = linkedTreeMap.get("endTime").toString().split(":");
            endTimeResult = eTime[0]+":"+eTime[1];
            endTime.setText(endTimeResult);

            performanceName.setText(linkedTreeMap.get("name").toString());
            speaker.setText(linkedTreeMap.get("speaker").toString());
        }
    }
}
