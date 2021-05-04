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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.LinkedTreeMap;
import com.raycoarana.codeinputview.CodeInputView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Event_Information_Page extends Fragment {


    private Integer state;
    private Integer bidState = null;
    private int bidId = 0;
    private int privateState;
    private String code;
    private ImageView privateIndicator;
    private TextView privateNote;


    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String image;
    private String place;
    private Integer id;

    private TextView EInfName;
    private TextView EInDescription;
    private TextView EInDate;
    private TextView EInPlace;
    private ImageView EInImage;

    public Event_Information_Page() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_information_page, container, false);
        view.setVisibility(View.GONE);
        final TextView signUp = view.findViewById(R.id.signUp);
        final Context context = getContext();

        EInfName = view.findViewById(R.id.EInfName);
        EInDescription = view.findViewById(R.id.EInDescription);
        EInDate = view.findViewById(R.id.EInDate);
        EInPlace = view.findViewById(R.id.EInPlace);
        EInImage = view.findViewById(R.id.EInImage);
        privateIndicator = view.findViewById(R.id.privateIndicatorInfo);
        privateNote = view.findViewById(R.id.privateNote);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        state = getArguments().getInt("state");
        id = Integer.parseInt(getArguments().getString("id"));


        SQLiteDatabase db = getContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM user;", null);
        query.moveToFirst();
        final int idSQL = query.getInt(query.getColumnIndex("id"));
        String passwordSQL = query.getString(query.getColumnIndex("password"));
        query.close();


        final String authorization = idSQL + " " + passwordSQL;

        NetworkService.getInstance()
                .getJSONApi()
                .getEventsData(authorization, null, null, null)
                .enqueue(new Callback<EventData>() {
                    @Override
                    public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                        EventData eventData = response.body();
                        final List obj = eventData.getObj();

                        for (int i = 0; i < obj.size(); i++) {
                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(i);
                            int idEvent = Integer.parseInt(linkedTreeMap.get("id").toString());
                            if (idEvent == id) {
                                name = linkedTreeMap.get("name").toString();
                                description = linkedTreeMap.get("description").toString();
                                startDate = linkedTreeMap.get("startDate").toString();
                                endDate = linkedTreeMap.get("endDate").toString();
                                image = linkedTreeMap.get("image").toString();
                                place = linkedTreeMap.get("place").toString();
                                privateState = Integer.parseInt(linkedTreeMap.get("private").toString());

                                break;
                            }
                        }

                        DateWork dateWork = new DateWork();
                        String dateResult = dateWork.getDate(startDate, endDate);

                        EInfName.setText(name);
                        EInDate.setText(dateResult);
                        EInDescription.setText(description);
                        EInPlace.setText(place);
                        if (image != null && !image.equals(""))
                            Picasso.get().load(image).resize(0, 1000).into(EInImage);


                        if (privateState != 0) {
                            NetworkService.getInstance()
                                    .getJSONApi()
                                    .getBidsData(authorization, idSQL, id, null)
                                    .enqueue(new Callback<GeneralData<BidsData>>() {
                                        @Override
                                        public void onResponse(@NonNull Call<GeneralData<BidsData>> call, @NonNull Response<GeneralData<BidsData>> response) {
                                            GeneralData<BidsData> bidsDataGeneralData = response.body();
                                            if (bidsDataGeneralData.getData().getAPIData().size() != 0) {
                                                BidsData bidsData = bidsDataGeneralData.getData().getAPIData().get(0);
                                                bidState = bidsData.getState_id();
                                                bidId = bidsData.getId();
                                                code = bidsData.getCode();
                                            }
                                            view.setVisibility(View.VISIBLE);


                                            if (state == 0) {
                                                ((MainActivity) getActivity()).setSwitchState(0);
                                                signUp.setText("Отменить заявку");
                                                signUp.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        NetworkService.getInstance()
                                                                .getJSONApi()
                                                                .deleteBid(authorization, bidId)
                                                                .enqueue(new Callback<Object>() {
                                                                    @Override
                                                                    public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                                        Toast.makeText(getContext(), "Заявка отменена", Toast.LENGTH_SHORT).show();
                                                                        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                                        manager.popBackStack();

                                                                    }

                                                                    @Override
                                                                    public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                                                                        signUp.setVisibility(View.VISIBLE);
                                                                        progressBar.setVisibility(View.GONE);
                                                                        Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                        t.printStackTrace();
                                                                    }
                                                                });
                                                    }
                                                });


                                            } else if (state == 1) {
                                                ((MainActivity) getActivity()).setSwitchState(1);
                                                signUp.setText("Ввести код подтверждения");
                                                final EditText editText = view.findViewById(R.id.code_input);
                                                editText.setVisibility(View.VISIBLE);
                                                signUp.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View view) {

                                                        if (editText.getText().toString().equals(code)) {
                                                            final BidsData bidsData = new BidsData(bidId, idSQL, id, code, 4);
                                                            NetworkService.getInstance()
                                                                    .getJSONApi()
                                                                    .updateBid(authorization, bidsData)
                                                                    .enqueue(new Callback<GeneralData<BidsData>>() {
                                                                        @Override
                                                                        public void onResponse(@NonNull Call<GeneralData<BidsData>> call, @NonNull Response<GeneralData<BidsData>> response) {
                                                                            Toast.makeText(getContext(), "Эвент активирован", Toast.LENGTH_SHORT).show();
                                                                            ((MainActivity) getActivity()).setCurrentEvent(id);
                                                                            ((MainActivity) getActivity()).StartClassicView();
                                                                        }

                                                                        @Override
                                                                        public void onFailure(@NonNull Call<GeneralData<BidsData>> call, @NonNull Throwable t) {
                                                                            signUp.setVisibility(View.VISIBLE);
                                                                            progressBar.setVisibility(View.GONE);
                                                                            Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                            t.printStackTrace();
                                                                        }
                                                                    });


                                                        } else {
                                                            Toast.makeText(getContext(), "Неверный код", Toast.LENGTH_SHORT).show();
                                                            editText.setText("");
                                                        }
                                                    }
                                                });
                                            } else if (state == 2) {
                                                privateIndicator.setVisibility(View.VISIBLE);
                                                privateNote.setVisibility(View.VISIBLE);

                                                if (bidState == null) {
                                                    final BidsData bidsData = new BidsData(null, idSQL, id, null, 1);
                                                    signUp.setText("Отправить заявку");
                                                    signUp.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            signUp.setVisibility(View.GONE);
                                                            progressBar.setVisibility(View.VISIBLE);
                                                            if (signUp.getText().equals("Отправить заявку")) {
                                                                NetworkService.getInstance()
                                                                        .getJSONApi()
                                                                        .createBid(authorization, bidsData)
                                                                        .enqueue(new Callback<GeneralData<BidsData>>() {
                                                                            @Override
                                                                            public void onResponse(@NonNull Call<GeneralData<BidsData>> call, @NonNull Response<GeneralData<BidsData>> response) {
                                                                                Toast.makeText(getContext(), "Заявка отправленна", Toast.LENGTH_SHORT).show();
                                                                                GeneralData<BidsData> bidsDataGeneralData = response.body();
                                                                                bidId = bidsDataGeneralData.getData().getAPIData().get(0).getId();
                                                                                signUp.setText("Отменить заявку");
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(@NonNull Call<GeneralData<BidsData>> call, @NonNull Throwable t) {
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                                t.printStackTrace();
                                                                            }
                                                                        });
                                                            } else if (signUp.getText().equals("Отменить заявку")) {
                                                                NetworkService.getInstance()
                                                                        .getJSONApi()
                                                                        .deleteBid(authorization, bidId)
                                                                        .enqueue(new Callback<Object>() {
                                                                            @Override
                                                                            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                                                Toast.makeText(getContext(), "Заявка отменена", Toast.LENGTH_SHORT).show();
                                                                                Object object = response.body();
                                                                                signUp.setText("Отправить заявку");
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                                t.printStackTrace();
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });
                                                } else if (bidState == 1) {
                                                    signUp.setText("Отменить заявку");
                                                    final BidsData bidsData = new BidsData(null, idSQL, id, null, 1);
                                                    signUp.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            signUp.setVisibility(View.GONE);
                                                            progressBar.setVisibility(View.VISIBLE);
                                                            if (signUp.getText().equals("Отправить заявку")) {
                                                                NetworkService.getInstance()
                                                                        .getJSONApi()
                                                                        .createBid(authorization, bidsData)
                                                                        .enqueue(new Callback<GeneralData<BidsData>>() {
                                                                            @Override
                                                                            public void onResponse(@NonNull Call<GeneralData<BidsData>> call, @NonNull Response<GeneralData<BidsData>> response) {
                                                                                Toast.makeText(getContext(), "Заявка отправленна", Toast.LENGTH_SHORT).show();
                                                                                GeneralData<BidsData> bidsDataGeneralData = response.body();
                                                                                bidId = bidsDataGeneralData.getData().getAPIData().get(0).getId();
                                                                                signUp.setText("Отменить заявку");
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(@NonNull Call<GeneralData<BidsData>> call, @NonNull Throwable t) {
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                                t.printStackTrace();
                                                                            }
                                                                        });
                                                            } else if (signUp.getText().equals("Отменить заявку")) {
                                                                NetworkService.getInstance()
                                                                        .getJSONApi()
                                                                        .deleteBid(authorization, bidId)
                                                                        .enqueue(new Callback<Object>() {
                                                                            @Override
                                                                            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                                                                                Toast.makeText(getContext(), "Заявка отменена", Toast.LENGTH_SHORT).show();
                                                                                Object object = response.body();
                                                                                signUp.setText("Отправить заявку");
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                                                                                signUp.setVisibility(View.VISIBLE);
                                                                                progressBar.setVisibility(View.GONE);
                                                                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                                                t.printStackTrace();
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });
                                                } else if (bidState == 2) {
                                                    signUp.setVisibility(View.GONE);
                                                    privateNote.setText("Ваша заявка по этому мероприятию была отклонена, обратитесь к администратору");
                                                } else if (bidState == 3) {
                                                    signUp.setVisibility(View.GONE);
                                                    privateNote.setText("Ваша заявка принята, перейдите в профиль чтобы активировать мероприятие");
                                                } else if (bidState == 4) {
                                                    ((MainActivity) getActivity()).setCurrentEvent(id);
                                                    signUp.setVisibility(View.GONE);
                                                    progressBar.setVisibility(View.VISIBLE);

                                                    NetworkService.getInstance()
                                                            .getJSONApi()
                                                            .getUserData(authorization, idSQL)
                                                            .enqueue(new Callback<EventData>() {
                                                                @Override
                                                                public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                                                    EventData eventData = response.body();
                                                                    final List obj = eventData.getObj();
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
                                                                            id.toString(),
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
                                                                                    ((MainActivity) getActivity()).StartClassicView();

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

                                            }

                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<GeneralData<BidsData>> call, @NonNull Throwable t) {

                                            t.printStackTrace();
                                        }
                                    });
                        } else view.setVisibility(View.VISIBLE);


                        if ((state == 2 && privateState == 0)) {
                            signUp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((MainActivity) getActivity()).setCurrentEvent(id);
                                    signUp.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);

                                    NetworkService.getInstance()
                                            .getJSONApi()
                                            .getUserData(authorization, idSQL)
                                            .enqueue(new Callback<EventData>() {
                                                @Override
                                                public void onResponse(@NonNull Call<EventData> call, @NonNull Response<EventData> response) {
                                                    EventData eventData = response.body();
                                                    final List obj = eventData.getObj();
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
                                                            id.toString(),
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
                                                                    ((MainActivity) getActivity()).StartClassicView();

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
                        }


                    }

                    @Override
                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

        return view;
    }


}
