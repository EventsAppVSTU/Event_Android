package com.example.eventsapp;

import android.content.ClipData;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View itemMenu;
    private BottomNavigationView navView;
    private BottomNavigationView navView1;
    private Events_Page eventsPage;
    private Profile_Page profile_page;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            eventsPage = new Events_Page();
            profile_page = new Profile_Page();
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    return true;
                case R.id.navigation_dashboard:


                    return true;

                case R.id.navigation_notifications1:
                    SetFragment(profile_page);

                    return true;

                case R.id.navigation_dashboard1:
                SetFragment(eventsPage);

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
        navView.setVisibility(View.GONE);
        navView1.setVisibility(View.VISIBLE);
        navView1.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView1.setSelectedItemId(R.id.navigation_dashboard1);

        itemMenu = navView.findViewById(R.id.navigation_notifications);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);



    }
    private void SetFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }




}
