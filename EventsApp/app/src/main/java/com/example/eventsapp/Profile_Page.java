package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_Page extends Fragment {

    public Profile_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_page, container, false);
        CardView changeEvent = view.findViewById(R.id.changeEvent);
        TextView wipeCurrentEvent = view.findViewById(R.id.wipeCurrentEvent);
        final EventData[] eventData = {new EventData()};
//        final Integer id = null;

        if (!((MainActivity)getActivity()).CheckMenuState()){
            wipeCurrentEvent.setVisibility(View.GONE);
        }

        wipeCurrentEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String authorization = "27 ivan123";
                NetworkService.getInstance()
                        .getJSONApi()
                        .getUserData(authorization, 27)
                        .enqueue(new Callback<EventData>() {
                            @Override
                            public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                eventData[0] = response.body();
                                final List obj = eventData[0].getObj();
                                final LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                                UserData userData = new UserData(Integer.parseInt(linkedTreeMap.get("id").toString()), linkedTreeMap.get("name").toString(), linkedTreeMap.get("surname").toString(), linkedTreeMap.get("image").toString(), Integer.parseInt(linkedTreeMap.get("organization_id").toString()), "null", linkedTreeMap.get("login").toString(), linkedTreeMap.get("password").toString());





                                NetworkService.getInstance()
                                        .getJSONApi()
                                        .setCurrentEvent(authorization, userData)
                                        .enqueue(new Callback<Object>() {
                                            @Override
                                            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                Object object = response.body();
                                                ((MainActivity)getActivity()).StartStartView();



                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

                                                t.printStackTrace();
                                            }
                                        });





                            }

                            @Override
                            public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {

                                t.printStackTrace();
                            }
                        });



            }
        });






        return view;
    }
}
