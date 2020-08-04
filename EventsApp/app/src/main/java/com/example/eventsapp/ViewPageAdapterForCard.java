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

    public ViewPageAdapterForCard( Context context, List<List<Object>> Data) {
        this.context = context;
        this.data = Data;
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

        String dateResult = "";
        String[] sDate = linkedTreeMap.get("datePerf").toString().split(" ");
        String[] DMYs = sDate[0].split("-");

        String sMonth = "";

        switch (DMYs[1]) {
            case ("00"):
                sMonth = "не опознанная дата";
                break;
            case ("01"):
                sMonth = "января";
                break;
            case ("02"):
                sMonth = "февраля";
                break;
            case ("03"):
                sMonth = "марта";
                break;
            case ("04"):
                sMonth = "апреля";
                break;
            case ("05"):
                sMonth = "мая";
                break;
            case ("06"):
                sMonth = "июня";
                break;
            case ("07"):
                sMonth = "июля";
                break;
            case ("08"):
                sMonth = "августа";
                break;
            case ("09"):
                sMonth = "сентября";
                break;
            case ("10"):
                sMonth = "октября";
                break;
            case ("11"):
                sMonth = "ноября";
                break;
            case ("12"):
                sMonth = "декабря";
                break;
        }

        dateResult = DMYs[2] + " " + sMonth;
        date.setText(dateResult);

        RecyclerView TodayList = view.findViewById(R.id.TodayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        TodayList.setLayoutManager(layoutManager);
        AdapterForScheduleCard ScheduleAdapter;
        TodayList.setHasFixedSize(true);
        ScheduleAdapter = new AdapterForScheduleCard(data.get(position), context);
        TodayList.setAdapter(ScheduleAdapter);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
