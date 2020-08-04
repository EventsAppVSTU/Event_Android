package com.example.eventsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Option_Page extends Fragment {
    public Option_Page(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_registration, container, false);
        final Button regButton = view.findViewById(R.id.regButton);
        TextView regHead = view.findViewById(R.id.regHead);
        final EditText regName = view.findViewById(R.id.regName);
        final EditText regSurname = view.findViewById(R.id.regSurname);
        final EditText regEmail = view.findViewById(R.id.regEmail);
        final EditText regPassword = view.findViewById(R.id.regPassword);
        final ProgressBar progressBar = view.findViewById(R.id.regProgressBar);

        final String name = getArguments().getString("name");
        final String surname = getArguments().getString("surname");
        final String image = getArguments().getString("image");
        String current_event = getArguments().getString("current_event");
        if(current_event.equals("nothing")){
            current_event = "null";
        }
        final String login = getArguments().getString("login");
        final String password = getArguments().getString("password");
        final String organization_id = getArguments().getString("organization_id");
        final Integer id = Integer.parseInt(getArguments().getString("id"));

        regButton.setText("Сохранить");
        regHead.setText("Редактировать профиль");
        regName.setHint("Текущее имя: " + name);
        regSurname.setHint("Текущая фамилия: " + surname);
        regEmail.setHint(login);
        regEmail.setVisibility(View.GONE);
        regPassword.setHint("Изменить пароль");



        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        int idSQL = query.getInt(query.getColumnIndex("id"));
        String passwordSQL = query.getString( query.getColumnIndex("password") );


        final String authorization = idSQL + " "  + passwordSQL;
        final String finalCurrent_event = current_event;
        regButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (regName.getText().toString().length() != 0 && regSurname.getText().toString().length() != 0  && regPassword.getText().toString().length() != 0) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    regButton.setVisibility(View.GONE);
                                    UserData userData = new UserData(id, regName.getText().toString(), regSurname.getText().toString(), image, Integer.parseInt(organization_id), finalCurrent_event, login, regPassword.getText().toString());

                                    NetworkService.getInstance()
                                            .getJSONApi()
                                            .setCurrentEvent(authorization, userData)
                                            .enqueue(new Callback<Object>() {
                                                @Override
                                                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                    Toast.makeText(getContext(), "Данные успешно отредактированы", Toast.LENGTH_SHORT).show();
                                                    Object object = response.body();
                                                    SQLiteDatabase dbRequest = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                                                    dbRequest.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
                                                    String sqlInput1 = "DELETE FROM user";
                                                    dbRequest.execSQL(sqlInput1);
                                                    String sqlInput = "INSERT INTO user VALUES ('" + id + "', '" + login + "', '" + regPassword.getText().toString() + "');";
                                                    dbRequest.execSQL(sqlInput);
                                                    Context context = getContext();
                                                    ((MainActivity)getActivity()).clearBackStack();
                                                    Profile_Page profile_page = new Profile_Page();
                                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.container, profile_page);
                                                    fragmentTransaction.commit();
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

                                                    t.printStackTrace();
                                                }
                                            });
                                }
                                else {
                                    Toast.makeText(getContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


        return view;
    }
}
