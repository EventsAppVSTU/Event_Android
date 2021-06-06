package com.example.eventsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.LinkedTreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    private Bitmap bitmap = null;
    private int PICK_IMAGE_REQUEST = 1;
    ImageView regAvatar;
    ImageView avatarDelete;
    FullOrganizationsData fullOrganizationsData;
    HashMap<String, Integer> spinnerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final ScrollView regScrollView = findViewById(R.id.regContainer);
        regScrollView.setVisibility(View.GONE);
        final Spinner orgSpinner = findViewById(R.id.orgSpinner);
        final Button regButton = findViewById(R.id.regButton);
        final EditText regName = findViewById(R.id.regName);
        final EditText regSurname = findViewById(R.id.regSurname);
        final EditText regEmail = findViewById(R.id.regEmail);
        final EditText regPassword = findViewById(R.id.regPassword);
        final EditText regPhone = findViewById(R.id.regPhone);
        final EditText regBio = findViewById(R.id.regBio);
        final EditText regLink = findViewById(R.id.regLink);
        final ProgressBar progressBar = findViewById(R.id.regProgressBar);
        final Integer org = 2;
        CardView addPhoto = findViewById(R.id.addPhoto);
        spinnerMap = new HashMap<String, Integer>();


        avatarDelete = findViewById(R.id.avatarDelete);
        regAvatar = findViewById(R.id.regAvatar);


        NetworkService.getInstance()
                .getJSONApi()
                .getOrganizations()
                .enqueue(new Callback<FullOrganizationsData>() {
                    @Override
                    public void onResponse(@NonNull Call<FullOrganizationsData> call, @NonNull Response<FullOrganizationsData> response) {
                        regScrollView.setVisibility(View.VISIBLE);
                        fullOrganizationsData = response.body();
                        String[] spinnerArray = new String[fullOrganizationsData.getData().getOrganizationsData().size()];
                        String[] spinnerArraySort = new String[spinnerArray.length+1];
                        spinnerArraySort[0] = "Организация";
                        spinnerMap.put("Организация", null);


                        for (int i = 0; i < fullOrganizationsData.getData().getOrganizationsData().size(); i++){
                            spinnerArray[i] = fullOrganizationsData.getData().getOrganizationsData().get(i).getName();
                            spinnerMap.put(fullOrganizationsData.getData().getOrganizationsData().get(i).getName(), fullOrganizationsData.getData().getOrganizationsData().get(i).getId());
                        }

                        Arrays.sort(spinnerArray);
                        System.arraycopy(spinnerArray, 0, spinnerArraySort, 1, spinnerArray.length);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_element, spinnerArraySort);
                        orgSpinner.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(@NonNull Call<FullOrganizationsData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });



        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    chooseImage();
            }
        });

        avatarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regAvatar.setImageResource(R.drawable.ic_baseline_add_24);
                bitmap = null;
                avatarDelete.setVisibility(View.GONE);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                if (regName.getText().toString().length() != 0 &&
                        regSurname.getText().toString().length() != 0 &&
                        regEmail.getText().toString().length() != 0 &&
                        regPassword.getText().toString().length() != 0) {

                    final String bio = regBio.getText().length() == 0 ? "" : regBio.getText().toString();
                    final String phone = regPhone.getText().length() == 0 ? "" : regPhone.getText().toString();
                    final String link = regLink.getText().length() == 0 ? "" : regLink.getText().toString();
                    final Bitmap tmpBitmap = bitmap;
                    String organization = spinnerMap.get(orgSpinner.getSelectedItem().toString()) == null ? "null" : String.valueOf(spinnerMap.get(orgSpinner.getSelectedItem().toString()));


                    progressBar.setVisibility(View.VISIBLE);
                    regButton.setVisibility(View.GONE);




                    RegistrationData registrationData = new RegistrationData(
                            regName.getText().toString(),
                            regSurname.getText().toString(),
                            "null",
                            organization,
                            0,
                            "null",
                            regEmail.getText().toString(),
                            md5(regPassword.getText().toString()),
                            0,
                            phone,
                            link,
                            bio
                    );

                    NetworkService.getInstance()
                            .getJSONApi()
                            .createUser(registrationData)
                            .enqueue(new Callback<GeneralData<UserData>>() {
                                @Override
                                public void onResponse(@NonNull Call<GeneralData<UserData>> call, @NonNull Response<GeneralData<UserData>> response) {
                                    final GeneralData<UserData> generalData = response.body();

                                    if (generalData.getStatus().equals("ok")) {

                                        if (tmpBitmap != null) {

                                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                            tmpBitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);
                                            byte[] byteArray = outputStream.toByteArray();

                                            Time time = new Time();
                                            time.setToNow();
                                            FirebaseStorage storage = FirebaseStorage.getInstance();
                                            final StorageReference storageRef = storage.getReferenceFromUrl("gs://eventsapp-3754f.appspot.com/").
                                                    child(regEmail.getText().toString() + time.toMillis(false));

                                            UploadTask uploadTask = storageRef.putBytes(byteArray);
                                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                }
                                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {

                                                            UserData userData = generalData.getData().getAPIData().get(0);
                                                            userData.setImage(uri.toString());
                                                            userData.setCurrent_event("null");
                                                            if (generalData.getData().getAPIData().get(0).getOrganization_id() == null)
                                                                userData.setOrganization_id("null");
                                                            String authorization = userData.getId() + " "  + userData.getPassword();

                                                            NetworkService.getInstance()
                                                                    .getJSONApi()
                                                                    .setCurrentEvent(authorization, userData)
                                                                    .enqueue(new Callback<Object>() {
                                                                        @Override
                                                                        public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                                            Toast.makeText(getApplicationContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                                                                            Intent myIntent = new Intent(Registration.this, Authorization.class);
                                                                            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                            Registration.this.startActivity(myIntent);
                                                                            finish();
                                                                        }

                                                                        @Override
                                                                        public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                                                                            Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                            progressBar.setVisibility(View.GONE);
                                                                            regButton.setVisibility(View.VISIBLE);
                                                                            t.printStackTrace();
                                                                        }
                                                                    });
                                                        }
                                                    });
                                                }
                                            });

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                                            Intent myIntent = new Intent(Registration.this, Authorization.class);
                                            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            Registration.this.startActivity(myIntent);
                                            finish();
                                        }



                                    } else {
                                        Toast.makeText(getApplicationContext(), "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        regButton.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<GeneralData<UserData>> call, @NonNull Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Подключение к серверу отсутствует", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    regButton.setVisibility(View.VISIBLE);
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





    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                avatarDelete.setVisibility(View.VISIBLE);
                regAvatar.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }



}
