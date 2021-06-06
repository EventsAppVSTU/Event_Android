package com.example.eventsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bosphere.fadingedgelayout.FadingEdgeLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class My_Events_Page extends Fragment {

    RecyclerView myEventsList;
    FadingEdgeLayout myFadingEdgeLayout;
    TextView myEventsStatus;
    ProgressBar myProgressBar;


    public My_Events_Page(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_events_page, container, false);
        myEventsList = view.findViewById(R.id.myEventsList);
        myFadingEdgeLayout = view.findViewById(R.id.my_fading_edge_layout);
        myEventsStatus = view.findViewById(R.id.myEventsStatus);
        myProgressBar = view.findViewById(R.id.progressBarMyEvents);



        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        final int idSQL = query.getInt(query.getColumnIndex("id"));
        String passwordSQL = query.getString(query.getColumnIndex("password"));
        query.close();


        final String authorization = idSQL + " " + passwordSQL;

        myProgressBar.setVisibility(View.VISIBLE);

        NetworkService.getInstance()
                .getJSONApi()
                .getChosenEvents(authorization, ((MainActivity) getActivity()).getCurrentEvent(), idSQL)
                .enqueue(new Callback<GeneralData<ChosenEventsData>>() {
                    @Override
                    public void onResponse(@NonNull Call<GeneralData<ChosenEventsData>> call, @NonNull Response<GeneralData<ChosenEventsData>> response) {
                        myProgressBar.setVisibility(View.GONE);
                        GeneralData<ChosenEventsData> generalData = response.body();
                        if (generalData.getData().getAPIData().size() == 0) {
                            myEventsStatus.setVisibility(View.VISIBLE);
                        } else {
                            myFadingEdgeLayout.setFadeEdges(true, false, true, false);
                            myFadingEdgeLayout.setFadeSizes(40, 40, 40, 40);
                            myFadingEdgeLayout.setVisibility(View.VISIBLE);

                            myEventsList.setVisibility(View.VISIBLE);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            myEventsList.setLayoutManager(layoutManager);
                            AdapterForMyEventsList bidsAdapter = new AdapterForMyEventsList(generalData.getData().getAPIData(), getContext());
                            myEventsList.setAdapter(bidsAdapter);
                            myEventsList.setHasFixedSize(true);
                        }

                    }
                    @Override
                    public void onFailure(@NonNull Call<GeneralData<ChosenEventsData>> call, @NonNull Throwable t) {

                        t.printStackTrace();
                    }
                });

        return view;
    }

}
