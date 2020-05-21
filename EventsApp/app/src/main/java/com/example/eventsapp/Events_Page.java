package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Events_Page extends Fragment {

    public Events_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.events_page, container, false);
        Context context = getContext();

        RecyclerView eventsList = view.findViewById(R.id.eventsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        eventsList.setLayoutManager(layoutManager);
        AdapterForEventsList EventsAdapter = new AdapterForEventsList(10, context);
        eventsList.setAdapter(EventsAdapter);
        eventsList.setHasFixedSize(true);


        return view;
    }
}
