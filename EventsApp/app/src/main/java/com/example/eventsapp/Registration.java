package com.example.eventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EventData[] eventData = {new EventData()};
        final Button regButton = findViewById(R.id.regButton);
        final EditText regName = findViewById(R.id.regName);
        final EditText regSurname = findViewById(R.id.regSurname);
        final EditText regEmail = findViewById(R.id.regEmail);
        final EditText regPassword = findViewById(R.id.regPassword);
        final ProgressBar progressBar = findViewById(R.id.regProgressBar);
        final Integer org = 2;

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (regName.getText().toString().length() != 0 && regSurname.getText().toString().length() != 0 && regEmail.getText().toString().length() != 0 && regPassword.getText().toString().length() != 0) {
                    RegistrationData registrationData = new RegistrationData(regName.getText().toString(), regSurname.getText().toString(), "null", org, "null", regEmail.getText().toString(), regPassword.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    regButton.setVisibility(View.GONE);
                    NetworkService.getInstance()
                            .getJSONApi()
                            .getRegistration(registrationData)
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                    Object object = response.body();
//                                    eventData[0] = response.body();
//                                    final List obj = eventData[0].getObj();
                                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) object;
                                    if (linkedTreeMap.get("status").toString().equals("ok")) {
                                        Toast.makeText(getApplicationContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(Registration.this, Authorization.class);
                                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Registration.this.startActivity(myIntent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        regButton.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

                                    t.printStackTrace();
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
