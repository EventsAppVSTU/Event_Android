package com.example.eventsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Event_Information_Page extends Fragment {

    public Event_Information_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_information_page, container, false);
        final TextView signUp = view.findViewById(R.id.signUp);
        final Context context = getContext();
        final EventData[] eventData = {new EventData()};


        TextView EInfName = view.findViewById(R.id.EInfName);
        TextView EInDescription = view.findViewById(R.id.EInDescription);
        TextView EInDate = view.findViewById(R.id.EInDate);
        TextView EInPlace = view.findViewById(R.id.EInPlace);
        ImageView EInImage = view.findViewById(R.id.EInImage);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        String name = getArguments().getString("name");
        String description = getArguments().getString("description");
        String startDate = getArguments().getString("startDate");
        String endDate = getArguments().getString("endDate");
        String image = getArguments().getString("image");
        String place = getArguments().getString("place");
        final Integer id = Integer.parseInt(getArguments().getString("id"));

        String dateResult = "";
        String [] sDate = startDate.split(" ");
        String [] eDate = endDate.split(" ");
        String [] DMYs = sDate[0].split("-");
        String [] DMYe = eDate[0].split("-");

        String sMonth = "";
        String eMonth = "";

        switch (DMYs[1]){
            case ("00"):
                sMonth = "не опознанная дата";
                break;
            case ("01"):
                sMonth = "января";
                break;
            case ("02"):
                sMonth = "февраля";
                break;
            case ("03"):
                sMonth = "марта";
                break;
            case ("04"):
                sMonth = "апреля";
                break;
            case ("05"):
                sMonth = "мая";
                break;
            case ("06"):
                sMonth = "июня";
                break;
            case ("07"):
                sMonth = "июля";
                break;
            case ("08"):
                sMonth = "августа";
                break;
            case ("09"):
                sMonth = "сентября";
                break;
            case ("10"):
                sMonth = "октября";
                break;
            case ("11"):
                sMonth = "ноября";
                break;
            case ("12"):
                sMonth = "декабря";
                break;
        }

        switch (DMYe[1]){
            case ("00"):
                eMonth = "не опознанная дата";
                break;
            case ("01"):
                eMonth = "января";
                break;
            case ("02"):
                eMonth = "февраля";
                break;
            case ("03"):
                eMonth = "марта";
                break;
            case ("04"):
                eMonth = "апреля";
                break;
            case ("05"):
                eMonth = "мая";
                break;
            case ("06"):
                eMonth = "июня";
                break;
            case ("07"):
                eMonth = "июля";
                break;
            case ("08"):
                eMonth = "августа";
                break;
            case ("09"):
                eMonth = "сентября";
                break;
            case ("10"):
                eMonth = "октября";
                break;
            case ("11"):
                eMonth = "ноября";
                break;
            case ("12"):
                eMonth = "декабря";
                break;
        }

        if(sMonth.equals(eMonth)){
            dateResult += DMYs[2] + " - " + DMYe[2] + " " + sMonth;
        }
        else {
            dateResult += DMYs[2] + " " + sMonth + " - " + DMYe[2] + " " + eMonth;
        }

        EInfName.setText(name);
        EInDate.setText(dateResult);
        EInDescription.setText(description);
        EInPlace.setText(place);
        Picasso.get().load(image).resize(0, 1000).into(EInImage);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setCurrentEvent(id);
                signUp.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
                Cursor query = db.rawQuery("SELECT * FROM user;", null);
                query.moveToFirst();
                int idSQL = query.getInt(query.getColumnIndex("id"));
                String passwordSQL = query.getString( query.getColumnIndex("password") );


                final String authorization = idSQL + " "  + passwordSQL;
                NetworkService.getInstance()
                        .getJSONApi()
                        .getUserData(authorization, idSQL)
                        .enqueue(new Callback<EventData>() {
                            @Override
                            public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                eventData[0] = response.body();
                                final List obj = eventData[0].getObj();
                                final LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                                UserData userData = new UserData(Integer.parseInt(linkedTreeMap.get("id").toString()), linkedTreeMap.get("name").toString(), linkedTreeMap.get("surname").toString(), linkedTreeMap.get("image").toString(), Integer.parseInt(linkedTreeMap.get("organization_id").toString()), id.toString(), linkedTreeMap.get("login").toString(), linkedTreeMap.get("password").toString());


                                NetworkService.getInstance()
                                        .getJSONApi()
                                        .setCurrentEvent(authorization, userData)
                                        .enqueue(new Callback<Object>() {
                                            @Override
                                            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                Object object = response.body();
                                                ((MainActivity)getActivity()).StartClassicView();




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













//                Time_Table_Page time_table_page = new Time_Table_Page();
//                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
////                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container, time_table_page);
//                fragmentTransaction.commit();
            }
        });





        return view;
    }



}
