package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News_Page extends Fragment {
    EventData eventData;
    EditText Search;
    TextView header;
    TextView newsStatus;
    public News_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.events_page, container, false);
        final Context context = getContext();
        newsStatus = view.findViewById(R.id.newsStatus);
        Search = view.findViewById(R.id.search);
        header = view.findViewById(R.id.header);
        header.setText("Новости");

        String auothorization = "1 111111_2";
        NetworkService.getInstance()
                .getJSONApi()
                .getEventNews(auothorization, ((MainActivity)getActivity()).getCurrentEvent())
                .enqueue(new Callback<EventData>() {
                    @Override
                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                        eventData = response.body();
                        final List obj = eventData.getObj();
                        if(obj.size()!=0) {
                            final LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                            String id = linkedTreeMap.get("id").toString();

                            final RecyclerView eventsList = view.findViewById(R.id.eventsList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            eventsList.setLayoutManager(layoutManager);
                            final AdapterForEventsList[] EventsAdapter = {new AdapterForEventsList(context, obj, header)};
                            eventsList.setAdapter(EventsAdapter[0]);
                            eventsList.setHasFixedSize(true);


                            Search.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    String userInput = charSequence.toString();
                                    List newObj = new ArrayList();
//                                List<Food> NewFood = new ArrayList<>();


                                    for (int k = 0; k < obj.size(); k++) {
                                        LinkedTreeMap linkedTreeMapNew = (LinkedTreeMap) obj.get(k);
                                        if (Pattern.compile(Pattern.quote(userInput), Pattern.CASE_INSENSITIVE).matcher(linkedTreeMapNew.get("name").toString()).find()) {
                                            newObj.add(obj.get(k));
//                        Pattern.compile(Pattern.quote(Food.getName()), Pattern.CASE_INSENSITIVE).matcher(userInput).find();
//                        Food.getName().contains(userInput)
                                        }
                                    }

                                    EventsAdapter[0] = new AdapterForEventsList(context, newObj, header);
//                                adapterForSearch[0] = new AdapterForSearch(getContext(), NewFood, mchipGroup, view);
                                    eventsList.setAdapter(EventsAdapter[0]);

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                        }
                        else {
                            newsStatus.setVisibility(View.VISIBLE);
                        }




                    }

                    @Override
                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {

                        t.printStackTrace();
                    }
                });




        return view;
    }
}
