package com.example.eventsapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bosphere.fadingedgelayout.FadingEdgeLayout;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Long.toUnsignedString;

public class Bids_Page extends Fragment {

    RecyclerView bidsList;
    FadingEdgeLayout fadingEdgeLayout;
    TextView bidsStatus;
    ProgressBar progressBarBids;

    public Bids_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bids_page, container, false);


        LabeledSwitch labeledSwitch = view.findViewById(R.id.switch4);
        bidsList = view.findViewById(R.id.bidsList);
        fadingEdgeLayout = view.findViewById(R.id.fading_edge_layout);
        bidsStatus = view.findViewById(R.id.bidsStatus);
        progressBarBids = view.findViewById(R.id.progressBarBids);


        FadingEdgeLayout mFadingEdgeLayout = view.findViewById(R.id.fading_edge_layout) ;
        mFadingEdgeLayout.setFadeEdges(true, false, true, false);
        mFadingEdgeLayout.setFadeSizes(50, 50, 50, 50);


        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        final int idSQL = query.getInt(query.getColumnIndex("id"));
        String passwordSQL = query.getString( query.getColumnIndex("password") );
        query.close();


        final String authorization = idSQL + " "  + passwordSQL;


       int switchState = ((MainActivity)getActivity()).getSwitchState();
       if (switchState == 0){
           labeledSwitch.setOn(false);
       } else {
           labeledSwitch.setOn(true);
       }



        if(!labeledSwitch.isOn()){
            progressBarBids.setVisibility(View.VISIBLE);
            NetworkService.getInstance()
                    .getJSONApi()
                    .getFullBidsData(authorization, idSQL, null, 1)
                    .enqueue(new Callback<GeneralData<FullBidsData>>() {
                        @Override
                        public void onResponse(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Response<GeneralData<FullBidsData>> response) {
                            GeneralData<FullBidsData> fullBidsDataGeneralData = response.body();
                            List<FullBidsData> fullBidsData = fullBidsDataGeneralData.getData().getAPIData();
                            if (fullBidsData.size() == 0){
                                bidsStatus.setText("У вас нет активных заявок");
                                progressBarBids.setVisibility(View.GONE);
                                bidsStatus.setVisibility(View.VISIBLE);
                            } else {
                                fadingEdgeLayout.setVisibility(View.VISIBLE);
                                progressBarBids.setVisibility(View.GONE);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                bidsList.setLayoutManager(layoutManager);
                                AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(fullBidsData, getContext(), false);
                                bidsList.setAdapter(bidsAdapter);
                                bidsList.setHasFixedSize(true);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });

        }

        if(labeledSwitch.isOn()){
            progressBarBids.setVisibility(View.VISIBLE);
            NetworkService.getInstance()
                    .getJSONApi()
                    .getFullBidsData(authorization, idSQL, null, 3)
                    .enqueue(new Callback<GeneralData<FullBidsData>>() {
                        @Override
                        public void onResponse(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Response<GeneralData<FullBidsData>> response) {
                            GeneralData<FullBidsData> fullBidsDataGeneralData = response.body();
                            List<FullBidsData> fullBidsData = fullBidsDataGeneralData.getData().getAPIData();
                            if (fullBidsData.size() == 0){
                                bidsStatus.setText("У вас нет принятых заявок");
                                progressBarBids.setVisibility(View.GONE);
                                bidsStatus.setVisibility(View.VISIBLE);
                            } else {
                                fadingEdgeLayout.setVisibility(View.VISIBLE);
                                progressBarBids.setVisibility(View.GONE);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                bidsList.setLayoutManager(layoutManager);
                                AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(fullBidsData, getContext(), true);
                                bidsList.setAdapter(bidsAdapter);
                                bidsList.setHasFixedSize(true);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });

        }



        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {


                if (labeledSwitch.isOn()) {
                    bidsStatus.setVisibility(View.GONE);
                    fadingEdgeLayout.setVisibility(View.GONE);
                    progressBarBids.setVisibility(View.VISIBLE);
                    NetworkService.getInstance()
                            .getJSONApi()
                            .getFullBidsData(authorization, idSQL, null, 3)
                            .enqueue(new Callback<GeneralData<FullBidsData>>() {
                                @Override
                                public void onResponse(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Response<GeneralData<FullBidsData>> response) {
                                    GeneralData<FullBidsData> fullBidsDataGeneralData = response.body();
                                    List<FullBidsData> fullBidsData = fullBidsDataGeneralData.getData().getAPIData();
                                    if (fullBidsData.size() == 0){
                                        bidsStatus.setText("У вас нет принятых заявок");
                                        progressBarBids.setVisibility(View.GONE);
                                        bidsStatus.setVisibility(View.VISIBLE);
                                    } else {
                                        fadingEdgeLayout.setVisibility(View.VISIBLE);
                                        progressBarBids.setVisibility(View.GONE);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                        bidsList.setLayoutManager(layoutManager);
                                        AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(fullBidsData, getContext(), true);
                                        bidsList.setAdapter(bidsAdapter);
                                        bidsList.setHasFixedSize(true);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Throwable t) {
                                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    t.printStackTrace();
                                }
                            });
                }



                if (!labeledSwitch.isOn()) {
                    bidsStatus.setVisibility(View.GONE);
                    fadingEdgeLayout.setVisibility(View.GONE);
                    progressBarBids.setVisibility(View.VISIBLE);
                    NetworkService.getInstance()
                            .getJSONApi()
                            .getFullBidsData(authorization, idSQL, null, 1)
                            .enqueue(new Callback<GeneralData<FullBidsData>>() {
                                @Override
                                public void onResponse(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Response<GeneralData<FullBidsData>> response) {
                                    GeneralData<FullBidsData> fullBidsDataGeneralData = response.body();
                                    List<FullBidsData> fullBidsData = fullBidsDataGeneralData.getData().getAPIData();
                                    if (fullBidsData.size() == 0){
                                        bidsStatus.setText("У вас нет активных заявок");
                                        progressBarBids.setVisibility(View.GONE);
                                        bidsStatus.setVisibility(View.VISIBLE);
                                    } else {
                                        fadingEdgeLayout.setVisibility(View.VISIBLE);
                                        progressBarBids.setVisibility(View.GONE);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                        bidsList.setLayoutManager(layoutManager);
                                        AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(fullBidsData, getContext(), false);
                                        bidsList.setAdapter(bidsAdapter);
                                        bidsList.setHasFixedSize(true);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<GeneralData<FullBidsData>> call, @NonNull Throwable t) {
                                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    t.printStackTrace();
                                }
                            });

                }
            }
        });



        return view;
    }

}
