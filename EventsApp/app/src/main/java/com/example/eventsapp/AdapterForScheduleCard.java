package com.example.eventsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
    public ChosenPerformancesData.PerformancesArray performancesArray;
    private boolean state;
    private int user;

    public AdapterForScheduleCard(List<Object> Data, Context Context,
                                  ChosenPerformancesData.PerformancesArray performancesArray,
                                  boolean state, int user){
        this.data = Data;
        this.context = Context;
        this.performancesArray = performancesArray;
        this.state = state;
        this.user = user;

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
    public void onBindViewHolder(@NonNull final AdapterForScheduleCard.ScheduleViewHolder holder, final int position) {
        holder.bind(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) data.get(position);
                ChosenPerformancesData chosenPerformancesData = new ChosenPerformancesData(user,
                        Integer.parseInt(linkedTreeMap.get("id").toString()));
                if (holder.checkBox.isChecked()) {

                    performancesArray.performances_array.add(chosenPerformancesData);
                } else {
                    for (int i = 0; i < performancesArray.performances_array.size(); i++) {
                        if (performancesArray.performances_array.get(i).getPerformances_id() == chosenPerformancesData.getPerformances_id()){
                            performancesArray.performances_array.remove(i);
                            break;
                        }
                    }
                }
            }
        });

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
        CheckBox checkBox;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.timeStart);
            endTime = itemView.findViewById(R.id.timeEnd);
            performanceName = itemView.findViewById(R.id.performanceName);
            speaker = itemView.findViewById(R.id.speakerName);
            checkBox = itemView.findViewById(R.id.checkBoxTimeTable);
        }
        void bind(int Index) {

                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) data.get(Index);
                String startTimeResult = "";
                String[] sTime = linkedTreeMap.get("startTime").toString().split(":");
                startTimeResult = sTime[0] + ":" + sTime[1];
                startTime.setText(startTimeResult);

                String endTimeResult = "";
                String[] eTime = linkedTreeMap.get("endTime").toString().split(":");
                endTimeResult = eTime[0] + ":" + eTime[1];
                endTime.setText(endTimeResult);

                if (linkedTreeMap.get("performances_name") == null) {
                    performanceName.setText(linkedTreeMap.get("performance_name").toString());
                } else {
                    performanceName.setText(linkedTreeMap.get("performances_name").toString());
                }


                speaker.setText(linkedTreeMap.get("speaker").toString());

                if (!state) checkBox.setVisibility(View.VISIBLE);

        }
    }
}
