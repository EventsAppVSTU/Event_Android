package com.example.eventsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
