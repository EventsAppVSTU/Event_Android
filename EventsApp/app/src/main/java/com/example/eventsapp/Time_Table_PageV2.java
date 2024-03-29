package com.example.eventsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Time_Table_PageV2 extends Fragment {

    private ChosenPerformancesData.PerformancesArray performancesArray;
    private TextView chooseButton;

    public Time_Table_PageV2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.time_table_page_v2, container, false);


        performancesArray = new ChosenPerformancesData.PerformancesArray();
        performancesArray.performances_array = new ArrayList<>();
        chooseButton = view.findViewById(R.id.choosePerf);
        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        final int idSQL = query.getInt(query.getColumnIndex("id"));
        String passwordSQL = query.getString(query.getColumnIndex("password"));


        final String authorization = idSQL + " " + passwordSQL;


        NetworkService.getInstance()
                .getJSONApi()
                .getChosenPerf(authorization, ((MainActivity) getActivity()).getCurrentEvent(), idSQL)
                .enqueue(new Callback<EventData>() {
                    @Override
                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {


                        if (response.body().getObj().size() == 0) {
                        NetworkService.getInstance()
                                .getJSONApi()
                                .getTimeTable(authorization, ((MainActivity) getActivity()).getCurrentEvent())
                                .enqueue(new Callback<EventData>() {
                                    @Override
                                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                        EventData eventData = response.body();
                                        List obj = eventData.getObj();
                                        if (obj.size() != 0) {
                                            List<String> uniqueDate = new ArrayList<>();
                                            for (int i = 0; i < obj.size(); i++) {
                                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(i);
                                                String date = linkedTreeMap.get("datePerf").toString();
                                                if (!uniqueDate.contains(date)) {
                                                    uniqueDate.add(date);
                                                }
                                            }

                                            Collections.sort(uniqueDate);


//                            boolean isSorted = false;
//                            int buf;
//                            while(!isSorted) {
//                                isSorted = true;
//                                for (int i = 0; i < uniqueDate.size()-1; i++) {
//                                    String[] tmpD1 = uniqueDate.get(i).split(" ");
//                                    String[] DMY1 = tmpD1[0].split("-");
//
//                                    String[] tmpD2 = uniqueDate.get(i+1).split(" ");
//                                    String[] DMY2 = tmpD2[0].split("-");
//
//                                    if( Integer.parseInt()> mas[i+1]){
//                                        isSorted = false;
//
//                                        buf = mas[i];
//                                        mas[i] = mas[i+1];
//                                        mas[i+1] = buf;
//                                    }
//                                }
//                            }

                                            List<List<Object>> data = new ArrayList<>();
                                            for (int i = 0; i < uniqueDate.size(); i++) {
                                                List<Object> tmp = new ArrayList<>();
                                                for (int k = 0; k < obj.size(); k++) {
                                                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(k);
                                                    String date = linkedTreeMap.get("datePerf").toString();
                                                    if (date.equals(uniqueDate.get(i))) {
                                                        tmp.add(obj.get(k));
                                                    }
                                                }
                                                data.add(tmp);
                                            }
                                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                                            String id = linkedTreeMap.get("id").toString();

                                            Context context = getContext();
                                            ViewPageAdapterForCard viewPageAdapterForCard = new ViewPageAdapterForCard(context, data, performancesArray, false, idSQL);
                                            ViewPager viewPager = view.findViewById(R.id.mainViewPager);
                                            viewPager.setAdapter(viewPageAdapterForCard);
                                            viewPager.setPadding(55, 0, 55, 0);
                                            viewPager.setCurrentItem(0);

                                            chooseButton.setVisibility(View.VISIBLE);
                                            chooseButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    NetworkService.getInstance()
                                                            .getJSONApi()
                                                            .setChosenPerf(authorization, performancesArray)
                                                            .enqueue(new Callback<Object>() {
                                                                @Override
                                                                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {

                                                                    Time_Table_PageV2 time_table_pageV2 = new Time_Table_PageV2();
                                                                    SetFragment(time_table_pageV2);
                                                                }

                                                                @Override
                                                                public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                                                                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                    t.printStackTrace();
                                                                }
                                                            });
                                                }
                                            });


                                        } else {
                                            TextView nopeTT = view.findViewById(R.id.nopeTT);
                                            nopeTT.setVisibility(View.VISIBLE);
                                        }

                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {

                                        t.printStackTrace();
                                    }
                                });
                        } else {
                            NetworkService.getInstance()
                                    .getJSONApi()
                                    .getChosenPerf(authorization,  ((MainActivity) getActivity()).getCurrentEvent(), idSQL)
                                    .enqueue(new Callback<EventData>() {
                                        @Override
                                        public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                            EventData eventData = response.body();
                                            List obj = eventData.getObj();
                                            List<String> uniqueDate = new ArrayList<>();
                                            for (int i = 0; i < obj.size(); i++) {
                                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(i);
                                                String date = linkedTreeMap.get("datePerf").toString();
                                                if (!uniqueDate.contains(date)) {
                                                    uniqueDate.add(date);
                                                }
                                            }

                                            Collections.sort(uniqueDate);

                                            List<List<Object>> data = new ArrayList<>();
                                            for (int i = 0; i < uniqueDate.size(); i++) {
                                                List<Object> tmp = new ArrayList<>();
                                                for (int k = 0; k < obj.size(); k++) {
                                                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(k);
                                                    String date = linkedTreeMap.get("datePerf").toString();
                                                    if (date.equals(uniqueDate.get(i))) {
                                                        tmp.add(obj.get(k));
                                                    }
                                                }
                                                data.add(tmp);
                                            }
                                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                                            String id = linkedTreeMap.get("id").toString();

                                            Context context = getContext();
                                            ViewPageAdapterForCard viewPageAdapterForCard = new ViewPageAdapterForCard(context, data, performancesArray, true, idSQL);
                                            ViewPager viewPager = view.findViewById(R.id.mainViewPager);
                                            viewPager.setAdapter(viewPageAdapterForCard);
                                            viewPager.setPadding(55, 0, 55, 0);
                                            viewPager.setCurrentItem(0);
                                        }
                                        @Override
                                        public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {

                                            t.printStackTrace();
                                        }
                                    });

                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });


        return view;
    }

    private void SetFragment(Fragment fragment) {

        clearBackStack();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment, null);
        fragmentTransaction.commit();
    }

    public void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}


