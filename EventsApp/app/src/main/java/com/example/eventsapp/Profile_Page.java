package com.example.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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


public class Profile_Page extends Fragment {

    public Profile_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_page, container, false);
        view.setVisibility(View.GONE);
        CardView changeEvent = view.findViewById(R.id.changeEvent);
        CardView bids = view.findViewById(R.id.bids);
        final TextView wipeCurrentEvent = view.findViewById(R.id.wipeCurrentEvent);
        final EventData[] eventData = {new EventData()};
        final ProgressBar progressBar = view.findViewById(R.id.progressBar2);
        LinearLayout option = view.findViewById(R.id.option);
        LinearLayout contacts = view.findViewById(R.id.contacts);
        Button exitBtn = view.findViewById(R.id.exitBtn);
        final ImageView avatar = view.findViewById(R.id.avatar);
        final TextView userName = view.findViewById(R.id.userName);
        final TextView userSurname = view.findViewById(R.id.userSurname);
        final TextView phone = view.findViewById(R.id.phone);
        final TextView webLink = view.findViewById(R.id.web_link);
        final TextView userMail = view.findViewById(R.id.userMail);
        final TextView organization = view.findViewById(R.id.organization);
        final TextView orgVerify = view.findViewById(R.id.orgVerify);
        final TextView bio = view.findViewById(R.id.bio);

        if (!((MainActivity)getActivity()).CheckMenuState()){
            wipeCurrentEvent.setVisibility(View.GONE);
        }

        ((MainActivity)getActivity()).setSwitchState(0);




        SQLiteDatabase dbUser = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        Cursor query = dbUser.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        int idSQLG = query.getInt(query.getColumnIndex("id"));
        String passwordSQLG = query.getString( query.getColumnIndex("password") );
        query.close();

        final String authorization = idSQLG + " "  + passwordSQLG;
        NetworkService.getInstance()
                .getJSONApi()
                .getUserData(authorization, idSQLG)
                .enqueue(new Callback<EventData>() {
                    @Override
                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                        eventData[0] = response.body();
                        final List obj = eventData[0].getObj();
                        final LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                        userName.setText(linkedTreeMap.get("name").toString());
                        userSurname.setText(linkedTreeMap.get("surname").toString());
                        organization.setText(linkedTreeMap.get("organization_name").toString());
                        view.setVisibility(View.VISIBLE);
                        if (linkedTreeMap.get("image").toString() != null && !linkedTreeMap.get("image").toString().equals(""))
                            Picasso.get().load(linkedTreeMap.get("image").toString()).resize(0, 1000).into(avatar);

                        orgVerify.setVisibility(Integer.parseInt(linkedTreeMap.get("organization_verify").toString()) == 0 ? View.VISIBLE : View.GONE );

                        phone.setText(linkedTreeMap.get("phone") == null ? "нет данных" :
                                linkedTreeMap.get("phone").toString().equals("") ? "нет данных" : linkedTreeMap.get("phone").toString());

                        webLink.setText(linkedTreeMap.get("web_link") == null ? "нет данных" :
                                linkedTreeMap.get("web_link").toString().equals("") ? "нет данных" : linkedTreeMap.get("web_link").toString());

                        userMail.setText(linkedTreeMap.get("login") == null ? "нет данных" :
                                linkedTreeMap.get("login").toString().equals("") ? "нет данных" : linkedTreeMap.get("login").toString());

                        bio.setText(linkedTreeMap.get("bio") == null ? "нет данных" :
                                linkedTreeMap.get("bio").toString().equals("") ? "нет данных" : linkedTreeMap.get("bio").toString());


                    }
                    @Override
                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {

                        t.printStackTrace();
                    }
                });





//        support.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                String sqlInput = "DELETE FROM user";
                db.execSQL(sqlInput);
                Intent myIntent = new Intent(getContext(), Authorization.class);
                getContext().startActivity(myIntent);
                getActivity().finish();
            }
        });



        wipeCurrentEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wipeCurrentEvent.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                Cursor query = db.rawQuery("SELECT * FROM user;", null);
                query.moveToFirst();
                int idSQL = query.getInt(query.getColumnIndex("id"));
                String passwordSQL = query.getString( query.getColumnIndex("password") );
                query.close();

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
                                String org_id;
                                if (linkedTreeMap.get("organization_id") == null){
                                    org_id = null;
                                } else org_id = linkedTreeMap.get("organization_id").toString();
                                UserData userData = new UserData(
                                        Integer.parseInt(linkedTreeMap.get("id").toString()),
                                        linkedTreeMap.get("name").toString(),
                                        linkedTreeMap.get("surname").toString(),
                                        linkedTreeMap.get("image").toString(),
                                        org_id,
                                        0,
                                        "null",
                                        linkedTreeMap.get("login").toString(),
                                        linkedTreeMap.get("password").toString(),
                                        Integer.parseInt(linkedTreeMap.get("organization_verify").toString()),
                                        linkedTreeMap.get("phone").toString(),
                                        linkedTreeMap.get("web_link").toString(),
                                        linkedTreeMap.get("bio").toString()
                                );


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

        changeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Events_Page events_page = new Events_Page();
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, events_page);
                fragmentTransaction.addToBackStack("tag7").commit();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List obj = eventData[0].getObj();
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                Context context = getContext();
                Option_Page option_page = new Option_Page();
                Bundle args = new Bundle();
                args.putString("name", linkedTreeMap.get("name").toString());
                args.putString("surname", linkedTreeMap.get("surname").toString());
                args.putString("image", linkedTreeMap.get("image").toString());
                args.putString("organization_id", linkedTreeMap.get("organization_id").toString());
                if(linkedTreeMap.get("current_event") == null){
                    args.putString("current_event", "nothing");
                }
                else {
                args.putString("current_event", linkedTreeMap.get("current_event").toString());
                }
                args.putString("login", linkedTreeMap.get("login").toString());
                args.putString("password", linkedTreeMap.get("password").toString());
                args.putString("id", linkedTreeMap.get("id").toString());
                option_page.setArguments(args);
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, option_page);
                fragmentTransaction.addToBackStack("tag77").commit();
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Contacts_Page contacts_page = new Contacts_Page();
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();

                fragmentTransaction.replace(R.id.content_main, contacts_page);
                fragmentTransaction.addToBackStack("tag1").commit();

            }
        });

        bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Bids_Page bids_page = new Bids_Page();
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, bids_page);
                fragmentTransaction.addToBackStack("tag777").commit();
            }
        });
        return view;
    }
}
