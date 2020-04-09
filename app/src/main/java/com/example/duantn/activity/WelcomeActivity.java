package com.example.duantn.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.duantn.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }
                },
                1000);
    }
}
