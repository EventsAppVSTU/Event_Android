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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Events_Page extends Fragment {
    EventData eventData;
    EditText Search;
    TextView header;
    public Events_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.events_page, container, false);
        final Context context = getContext();
        Search = view.findViewById(R.id.search);
        header = view.findViewById(R.id.header);

        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        int idSQL = query.getInt(query.getColumnIndex("id"));
        String passwordSQL = query.getString( query.getColumnIndex("password") );




        final String authorization = idSQL + " "  + passwordSQL;


        NetworkService.getInstance()
                .getJSONApi()
                .getEventsData(authorization)
                .enqueue(new Callback<EventData>() {
                    @Override
                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                        eventData = response.body();
                        final List obj = eventData.getObj();
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

                                for(int k = 0; k< obj.size(); k++){
                                    LinkedTreeMap linkedTreeMapNew = (LinkedTreeMap) obj.get(k);
                                    if (Pattern.compile(Pattern.quote(userInput), Pattern.CASE_INSENSITIVE).matcher(linkedTreeMapNew.get("name").toString()).find()){
                                        newObj.add(obj.get(k));
                                    }
                                }

                                EventsAdapter[0] = new AdapterForEventsList(context, newObj, header);
                                eventsList.setAdapter(EventsAdapter[0]);

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });




        return view;
    }
}
