package com.example.eventsapp;

import android.os.Build;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bosphere.fadingedgelayout.FadingEdgeLayout;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import static java.lang.Long.toUnsignedString;

public class Bids_Page extends Fragment {

    public Bids_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bids_page, container, false);
//        final Plug plug = new Plug();

        LabeledSwitch labeledSwitch = view.findViewById(R.id.switch4);


        FadingEdgeLayout mFadingEdgeLayout = view.findViewById(R.id.fading_edge_layout) ;
        mFadingEdgeLayout.setFadeEdges(true, false, true, false);
        mFadingEdgeLayout.setFadeSizes(50, 50, 50, 50);


       int switchState = ((MainActivity)getActivity()).getSwitchState();
       if (switchState == 0){
           labeledSwitch.setOn(false);
       } else {
           labeledSwitch.setOn(true);
       }



        if(!labeledSwitch.isOn()){
            RecyclerView bidsList = view.findViewById(R.id.bidsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            bidsList.setLayoutManager(layoutManager);
            AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(((MainActivity)getActivity()).getRawData(), getContext(), false);
            bidsList.setAdapter(bidsAdapter);
            bidsList.setHasFixedSize(true);
        }

        if(labeledSwitch.isOn()){
            RecyclerView bidsList = view.findViewById(R.id.bidsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            bidsList.setLayoutManager(layoutManager);
            AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(((MainActivity)getActivity()).getConfirmedData(), getContext(), true);
            bidsList.setAdapter(bidsAdapter);
            bidsList.setHasFixedSize(true);
        }



        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {


                if (labeledSwitch.isOn()) {
                    RecyclerView bidsList = view.findViewById(R.id.bidsList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    bidsList.setLayoutManager(layoutManager);
                    AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(((MainActivity)getActivity()).getConfirmedData(), getContext(), true);
                    bidsList.setAdapter(bidsAdapter);
                    bidsList.setHasFixedSize(true);
                }



                if (!labeledSwitch.isOn()) {

                    RecyclerView bidsList = view.findViewById(R.id.bidsList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    bidsList.setLayoutManager(layoutManager);
                    AdapterForBidsPage bidsAdapter = new AdapterForBidsPage(((MainActivity)getActivity()).getRawData(), getContext(), false);
                    bidsList.setAdapter(bidsAdapter);
                    bidsList.setHasFixedSize(true);

                }
            }
        });



        return view;
    }

}
