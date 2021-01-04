package com.example.eventsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.internal.LinkedTreeMap;
import com.raycoarana.codeinputview.CodeInputView;
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
        ImageView privateIndicator = view.findViewById(R.id.privateIndicatorInfo);
        TextView privateNote = view.findViewById(R.id.privateNote);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        final String name = getArguments().getString("name");
        final String description = getArguments().getString("description");
        final String startDate = getArguments().getString("startDate");
        final String endDate = getArguments().getString("endDate");
        final String image = getArguments().getString("image");
        final String place = getArguments().getString("place");
        int state = getArguments().getInt("state");
        int privateState = getArguments().getInt("private");
        final Integer id = Integer.parseInt(getArguments().getString("id"));

        DateWork dateWork = new DateWork();
        String dateResult = dateWork.getDate(startDate, endDate);



        EInfName.setText(name);
        EInDate.setText(dateResult);
        EInDescription.setText(description);
        EInPlace.setText(place);
        Picasso.get().load(image).resize(0, 1000).into(EInImage);

        if (state == 2 && privateState == 0){
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

            }


        });} else if (state == 1){
            ((MainActivity)getActivity()).setSwitchState(1);
            signUp.setText("Ввести код подтверждения");
            final EditText editText = view.findViewById(R.id.code_input);
            editText.setVisibility(View.VISIBLE);
            signUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(editText.getText().toString().equals("1234")){

                        Toast.makeText(getContext(), "Верный код", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Неверный код", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                    }
                }
            });
        } else if (state == 0){
            ((MainActivity)getActivity()).setSwitchState(0);
            signUp.setText("Отменить заявку");
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((MainActivity)getActivity()).removePlugElement(id);
                    Bids_Page bids_page = new Bids_Page();
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    manager.popBackStack();

                }
            });


        } else if (privateState == 1 ){
            privateIndicator.setVisibility(View.VISIBLE);
            privateNote.setVisibility(View.VISIBLE);
            boolean exist1 = false;
            for (int i = 0; i < ((MainActivity)getActivity()).getRawData().size(); i++) {
                if (((MainActivity) getActivity()).getRawData().get(i).getName().equals(name)) {
                    exist1 = true;
                }
            }
            if (exist1){
                signUp.setText("Отменить заявку");
            } else {
            signUp.setText("Отправить заявку");
            }
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean exist = false;
                    for (int i = 0; i < ((MainActivity)getActivity()).getRawData().size(); i++) {
                        if (((MainActivity) getActivity()).getRawData().get(i).getName().equals(name)) {
                            exist = true;
                        }
                    }
                    if (!exist){
                        signUp.setText("Отменить заявку");

                        Toast.makeText(getContext(), "Заявка отправленна", Toast.LENGTH_SHORT).show();
                        EventServerData eventServerData = new EventServerData(id, name, description, startDate,endDate, image, place);
                        ((MainActivity)getActivity()).addRawData(eventServerData);}
                    else {
                        signUp.setText("Отправить заявку");

                        Toast.makeText(getContext(), "Заявка отменена", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getActivity()).removePlugElement(id);
                    }


                }
            });
        }





        return view;
    }



}
