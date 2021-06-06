package com.example.eventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class ViewPageAdapterForCard extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<List<Object>> data;
    public ChosenPerformancesData.PerformancesArray performancesArray;
    private boolean state;
    private int user;


    public ViewPageAdapterForCard( Context context, List<List<Object>> Data,
                                   ChosenPerformancesData.PerformancesArray performancesArray,
                                   boolean state, int user) {
        this.context = context;
        this.data = Data;
        this.performancesArray = performancesArray;
        this.state = state;
        this.user = user;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.day_element, container, false);
        TextView date = view.findViewById(R.id.date);
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) data.get(position).get(0);
        AdapterForScheduleCard ScheduleAdapter;
        DateWork dateWork = new DateWork();
        String dateResult = dateWork.getPerfDate(linkedTreeMap.get("datePerf").toString());

        date.setText(dateResult);

        RecyclerView TodayList = view.findViewById(R.id.TodayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        TodayList.setLayoutManager(layoutManager);
        TodayList.setHasFixedSize(true);
        ScheduleAdapter = new AdapterForScheduleCard(data.get(position), context, performancesArray, state, user);
        TodayList.setAdapter(ScheduleAdapter);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
