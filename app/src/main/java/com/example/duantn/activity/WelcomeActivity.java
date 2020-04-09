package com.example.duantn.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.duantn.R;
import com.example.duantn.util.Constant;
import com.example.duantn.util.SharePreferenceUtil;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        boolean hasLogin = SharePreferenceUtil.getBooleanPerferences(this, Constant.HAS_LOGIN, false);

        if(hasLogin){
            goToMain();
        }else {
            goToLogin();
        }

    }

    private void goToLogin() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    }
                },
                1000);
    }

    private void goToMain() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }
                },
                1000);
    }
}
