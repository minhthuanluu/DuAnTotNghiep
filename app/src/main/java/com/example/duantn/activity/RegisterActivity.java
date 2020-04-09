package com.example.duantn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duantn.R;

public class RegisterActivity extends AppCompatActivity {
    EditText et_email,et_phone,et_pass,et_repass;
    Button bt_reg;
    ImageView iv_showpass,iv_reshowpass;
    TextView tv_gotoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_pass = findViewById(R.id.et_pass);
        et_repass = findViewById(R.id.et_repass);
        tv_gotoLogin = findViewById(R.id.tv_gotoLogin);
        iv_showpass = findViewById(R.id.iv_showpass);
        iv_reshowpass = findViewById(R.id.iv_reshowpass);
        bt_reg = findViewById(R.id.bt_reg);
    }

    private void onClick(){
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
