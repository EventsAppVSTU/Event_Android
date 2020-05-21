package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Time_Table_Page extends Fragment {

    public Time_Table_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.time_table_page, container, false);


        RecyclerView weekList = view.findViewById(R.id.weekList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        weekList.setLayoutManager(layoutManager);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        weekList.setLayoutManager(HorizontalLayout);
        AdapterForWeekList weekAdapter = new AdapterForWeekList(3);
        weekList.setAdapter(weekAdapter);
        weekList.setHasFixedSize(true);


        RecyclerView TTList = view.findViewById(R.id.timeTableList);
        RecyclerView.LayoutManager layoutManagerTT = new LinearLayoutManager(getContext());
        TTList.setLayoutManager(layoutManagerTT);
        AdapterForTtElementList TTAdapter = new AdapterForTtElementList(4);
        TTList.setAdapter(TTAdapter);
        TTList.setHasFixedSize(true);


        return view;
    }
}
