package com.example.eventsapp;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    Plug plug = new Plug();

    private int switchState = 0;
    private boolean menuState = false;

    private View itemMenu;
    private BottomNavigationView navView;
    private BottomNavigationView navView1;
    private Events_Page eventsPage;
    private Profile_Page profile_page;
    private Time_Table_PageV2 time_table_page;
    private News_Page news_page;
    private int currentEvent;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            eventsPage = new Events_Page();
            profile_page = new Profile_Page();
            time_table_page = new Time_Table_PageV2();
            news_page = new News_Page();
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    fragment = news_page;
                    SetFragment(fragment);

                    return true;
                case R.id.navigation_timeTable:
                    fragment = time_table_page;
                    SetFragment(fragment);

                    return true;
                case R.id.navigation_profile:
                    fragment = profile_page;
                    SetFragment(fragment);

                    return true;

                case R.id.navigation_profile1:
                    fragment = profile_page;
                    SetFragment(fragment);

                    return true;

                case R.id.navigation_events:
                    fragment = eventsPage;
                SetFragment(fragment);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView1 = findViewById(R.id.nav_view1);



        final EventData[] userData = {new EventData()};

        SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("EventsApp.db", MODE_PRIVATE, null);
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
                        userData[0] = response.body();
                        final List obj = userData[0].getObj();
                        final LinkedTreeMap linkedTreeMap = (LinkedTreeMap) obj.get(0);
                        if(linkedTreeMap.get("current_event") != null){
                            currentEvent = Integer.parseInt(linkedTreeMap.get("current_event").toString());
                            StartClassicView();
                        }
                        else {
                            StartStartView();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<EventData> call, @NonNull Throwable t) {

                        t.printStackTrace();
                    }
                });






//        navView1.setVisibility(View.VISIBLE);


//        itemMenu = navView.findViewById(R.id.navigation_notifications);




    }
    private void SetFragment(Fragment fragment){

        clearBackStack();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment, null);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void StartClassicView(){

        navView.getMenu().clear();
        navView.inflateMenu(R.menu.bottom_nav_menu);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_timeTable);
        menuState = true;

    }

    public void StartStartView(){

        navView.getMenu().clear();
        navView.inflateMenu(R.menu.bottom_nav_menu2);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_events);
        menuState = false;

    }

    public boolean CheckMenuState(){
        return menuState;
    }



    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public int getCurrentEvent(){
        return currentEvent;
    }

    public void setCurrentEvent(Integer id){
        currentEvent = id;
    }

    public void removePlugElement (int id){
        plug.deleteRawData(id);
    }

    public List<EventServerData> getRawData(){
        return plug.getRawData();
    }

    public void addRawData (EventServerData eventServerData){
        plug.addData(eventServerData);
    }

    public List<EventServerData> getConfirmedData(){
        return plug.getConfirmedData();
    }

    public int getSwitchState (){
        return switchState;
    }

    public void setSwitchState (int state){
        switchState = state;
    }





}
