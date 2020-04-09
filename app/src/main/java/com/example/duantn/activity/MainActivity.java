package com.example.duantn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.duantn.R;
import com.example.duantn.fragment.ChatFragment;
import com.example.duantn.fragment.HomeFragment;
import com.example.duantn.fragment.PostFragment;
import com.example.duantn.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        HomeFragment fr = new HomeFragment();
        FragmentTransaction fr1 = getSupportFragmentManager().beginTransaction();
        fr1.replace(R.id.content, fr, "");
        fr1.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:

                            HomeFragment fr = new HomeFragment();
                            FragmentTransaction fr1 = getSupportFragmentManager().beginTransaction();
                            fr1.replace(R.id.content, fr, "");
                            fr1.commit();
                            return true;
                        case R.id.nav_post:

                            PostFragment frU = new PostFragment();
                            FragmentTransaction frTU = getSupportFragmentManager().beginTransaction();
                            frTU.replace(R.id.content, frU, "");
                            frTU.commit();
                            return true;
                        case R.id.nav_chat:
                            ChatFragment frC = new ChatFragment();
                            FragmentTransaction frTC = getSupportFragmentManager().beginTransaction();
                            frTC.replace(R.id.content, frC, "");
                            frTC.commit();
                            return true;
                        case R.id.nav_profile:

                            ProfileFragment frP = new ProfileFragment();
                            FragmentTransaction frTP = getSupportFragmentManager().beginTransaction();
                            frTP.replace(R.id.content, frP, "");
                            frTP.commit();
                            return true;


                    }
                    return false;
                }
            };
}
