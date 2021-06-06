package com.example.eventsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.LinkedTreeMap;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.eventsapp.Registration.md5;

public class Option_Page extends Fragment {


    private static final int RESULT_OK = -1;
    private Bitmap bitmap = null;
    private final int PICK_IMAGE_REQUEST = 1;
    ImageView regAvatar;
    ImageView avatarDelete;
    FullOrganizationsData fullOrganizationsData;
    HashMap<String, Integer> spinnerMap;


    String name;
    String surname;
    String image;
    String bio;
    String phone;
    String web_link;
    Integer organization_verify;
    String current_event;

    String login;
    String password;
    String organization_id;
    Integer id;
    String organization_name;


    public Option_Page() {

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
        final EditText regPhone = view.findViewById(R.id.regPhone);
        final EditText regBio = view.findViewById(R.id.regBio);
        final EditText regLink = view.findViewById(R.id.regLink);
        final ScrollView regScrollView = view.findViewById(R.id.regContainer);
        final Spinner orgSpinner = view.findViewById(R.id.orgSpinner);
        CardView addPhoto = view.findViewById(R.id.addPhoto);


        spinnerMap = new HashMap<String, Integer>();
        avatarDelete = view.findViewById(R.id.avatarDelete);
        regAvatar = view.findViewById(R.id.regAvatar);

        name = getArguments().getString("name");
        surname = getArguments().getString("surname");
        image = getArguments().getString("image");
        bio = getArguments().getString("bio");
        phone = getArguments().getString("phone");
        web_link = getArguments().getString("web_link");
        organization_verify = Integer.parseInt(getArguments().getString("organization_verify"));
        current_event = getArguments().getString("current_event");

        login = getArguments().getString("login");
        password = getArguments().getString("password");
        organization_id = getArguments().getString("organization_id");
        id = Integer.parseInt(getArguments().getString("id"));
        organization_name = getArguments().getString("organization_name");

        regButton.setText("Сохранить");
        regHead.setText("Редактировать профиль");
        regName.setHint("Текущее имя: " + name);
        regSurname.setHint("Текущая фамилия: " + surname);
        regEmail.setHint(login);
        regEmail.setVisibility(View.GONE);
        regPassword.setHint("Новый пароль");
        regBio.setHint("Текущее описание: " + (bio == null ? "нет данных" : bio));
        regPhone.setHint("Текущий номер: " + (phone == null ? "нет данных" : phone));
        regLink.setHint("Текущая ссылка: " + (web_link == null ? "нет данных" : web_link));

        if (image != null && !image.equals("")) {
            avatarDelete.setVisibility(View.VISIBLE);
            Picasso.get().load(image).resize(0, 1000).into(regAvatar);
        }


        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        int idSQL = query.getInt(query.getColumnIndex("id"));
        final String passwordSQL = query.getString(query.getColumnIndex("password"));


        regScrollView.setVisibility(View.GONE);
        NetworkService.getInstance()
                .getJSONApi()
                .getOrganizations()
                .enqueue(new Callback<FullOrganizationsData>() {
                    @Override
                    public void onResponse(@NonNull Call<FullOrganizationsData> call, @NonNull Response<FullOrganizationsData> response) {
                        regScrollView.setVisibility(View.VISIBLE);
                        fullOrganizationsData = response.body();
                        String[] spinnerArray = new String[fullOrganizationsData.getData().getOrganizationsData().size()];
                        String[] spinnerArraySort = new String[spinnerArray.length + 1];
                        spinnerArraySort[0] = "Организация";
                        spinnerMap.put("Организация", null);


                        for (int i = 0; i < fullOrganizationsData.getData().getOrganizationsData().size(); i++) {
                            spinnerArray[i] = fullOrganizationsData.getData().getOrganizationsData().get(i).getName();
                            spinnerMap.put(fullOrganizationsData.getData().getOrganizationsData().get(i).getName(), fullOrganizationsData.getData().getOrganizationsData().get(i).getId());
                        }

                        Arrays.sort(spinnerArray);
                        System.arraycopy(spinnerArray, 0, spinnerArraySort, 1, spinnerArray.length);

                        int indexSelection = 0;
                        if (organization_id != null) {
                            for (int i = 0; i < spinnerArraySort.length; i++) {
                                if (organization_name.equals(spinnerArraySort[i])) {
                                    indexSelection = i;
                                    break;
                                }
                            }
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_element, spinnerArraySort);
                        orgSpinner.setAdapter(adapter);
                        orgSpinner.setSelection(indexSelection);

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
                image = null;
                avatarDelete.setVisibility(View.GONE);
            }
        });

        final String authorization = idSQL + " " + passwordSQL;
        final String finalCurrent_event = current_event;
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bitmap tmpBitmap = bitmap;
                name = regName.getText().toString().length() != 0 ? regName.getText().toString() : name;
                surname = regSurname.getText().toString().length() != 0 ? regSurname.getText().toString() : surname;
                password = regName.getText().toString().length() != 0 ? md5(regPassword.getText().toString()) : password;

                if (!orgSpinner.getSelectedItem().toString().equals(organization_name)) {
                    organization_id = spinnerMap.get(orgSpinner.getSelectedItem().toString()) == null ? null : String.valueOf(spinnerMap.get(orgSpinner.getSelectedItem().toString()));
                    organization_verify = 0;
                }

                phone = regPhone.getText().toString().length() != 0 ? regPhone.getText().toString() : phone;
                bio = regBio.getText().toString().length() != 0 ? regBio.getText().toString() : bio;
                web_link = regLink.getText().toString().length() != 0 ? regLink.getText().toString() : web_link;


                progressBar.setVisibility(View.VISIBLE);
                regButton.setVisibility(View.GONE);
                final UserData userData = new UserData(
                        id,
                        name,
                        surname,
                        image,
                        organization_id,
                        0,
                        finalCurrent_event,
                        login,
                        password,
                        organization_verify,
                        phone,
                        web_link,
                        bio
                );

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

                                    userData.setImage(uri.toString());
                                    NetworkService.getInstance()
                                            .getJSONApi()
                                            .setCurrentEvent(authorization, userData)
                                            .enqueue(new Callback<Object>() {
                                                @Override
                                                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                    Toast.makeText(getContext(), "Данные успешно отредактированы", Toast.LENGTH_SHORT).show();
                                                    SQLiteDatabase dbRequest = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                                                    dbRequest.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
                                                    String sqlInput1 = "DELETE FROM user";
                                                    dbRequest.execSQL(sqlInput1);
                                                    String sqlInput = "INSERT INTO user VALUES ('" + id + "', '" + login + "', '" + password + "');";
                                                    dbRequest.execSQL(sqlInput);
                                                    Context context = getContext();
                                                    ((MainActivity) getActivity()).clearBackStack();
                                                    Profile_Page profile_page = new Profile_Page();
                                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.content_main, profile_page);
                                                    fragmentTransaction.commit();
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

                                                    t.printStackTrace();
                                                }
                                            });


                                }
                            });
                        }
                    });

                } else {
                    NetworkService.getInstance()
                            .getJSONApi()
                            .setCurrentEvent(authorization, userData)
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                    Toast.makeText(getContext(), "Данные успешно отредактированы", Toast.LENGTH_SHORT).show();
                                    SQLiteDatabase dbRequest = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
                                    dbRequest.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER, login TEXT, password TEXT )");
                                    String sqlInput1 = "DELETE FROM user";
                                    dbRequest.execSQL(sqlInput1);
                                    String sqlInput = "INSERT INTO user VALUES ('" + id + "', '" + login + "', '" + password + "');";
                                    dbRequest.execSQL(sqlInput);
                                    Context context = getContext();
                                    ((MainActivity) getActivity()).clearBackStack();
                                    Profile_Page profile_page = new Profile_Page();
                                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content_main, profile_page);
                                    fragmentTransaction.commit();
                                }

                                @Override
                                public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

                                    t.printStackTrace();
                                }
                            });

                }
            }
        });


        return view;
    }


    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);

                avatarDelete.setVisibility(View.VISIBLE);
                regAvatar.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
