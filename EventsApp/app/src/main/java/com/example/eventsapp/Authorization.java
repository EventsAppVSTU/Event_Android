package com.example.eventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eventsapp.Registration.md5;

public class Authorization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorization);
        final EventData[] eventData = {new EventData()};
        TextView registration = findViewById(R.id.registration);
        final TextView authorizationEnter = findViewById(R.id.authorizationEnter);
        final EditText loginEnter = findViewById(R.id.loginEnter);
        final EditText passwordEnter = findViewById(R.id.passwordEnter);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
        Cursor query = db.rawQuery("SELECT * FROM user;", null);

        if (query.getCount()!=0) {
            Intent intent = new Intent(Authorization.this, MainActivity.class);
            Authorization.this.startActivity(intent);
            finish();
        } else {
            authorizationEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String login = loginEnter.getText().toString();
                    String password = passwordEnter.getText().toString();
                    if (loginEnter.getText().toString().length() != 0 && passwordEnter.getText().toString().length() != 0) {
                        password = md5(password);
                        progressBar.setVisibility(View.VISIBLE);
                        authorizationEnter.setVisibility(View.GONE);
                        NetworkService.getInstance()
                                .getJSONApi()
                                .getAuthorization(login, password)
                                .enqueue(new Callback<EventData>() {
                                    @Override
                                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                        eventData[0] = response.body();
                                        final List obj = eventData[0].getObj();
                                        if (obj != null) {
                                            final LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                                            SQLiteDatabase dbRequest = getApplicationContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                                            dbRequest.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");

                                            String sqlInput = "INSERT INTO user VALUES ('" + Integer.parseInt(linkedTreeMap.get("id").toString()) + "', '" + linkedTreeMap.get("login") + "', '" + linkedTreeMap.get("password") + "');";
                                            dbRequest.execSQL(sqlInput);
                                            Intent myIntent = new Intent(Authorization.this, MainActivity.class);
                                            Authorization.this.startActivity(myIntent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            authorizationEnter.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Подключение к серверу отсутствует", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        authorizationEnter.setVisibility(View.VISIBLE);
                                        t.printStackTrace();
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Registration.class);
                    startActivity(intent);
                }
            });
        }
    }


}
