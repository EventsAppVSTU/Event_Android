package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Event_Information_Page extends Fragment {

    public Event_Information_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_information_page, container, false);
        CardView signUp = view.findViewById(R.id.signUp);
        final Context context = getContext();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time_Table_Page time_table_page = new Time_Table_Page();
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, time_table_page);
                fragmentTransaction.commit();
            }
        });



        return view;
    }
}
