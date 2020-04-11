package com.example.duantn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duantn.R;
import com.example.duantn.activity.model.User;
import com.example.duantn.util.Constant;
import com.example.duantn.util.SharePreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText et_email,et_pass;
    Button bt_login;
    FirebaseAuth fAuth;
    TextView tv_gotoDangky;
    ProgressDialog pd_loading;
    ImageView iv_showpass;
    boolean showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        onClick();
    }

    private void PasswordStatus() {
        if(showPass==true){
            et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            iv_showpass.setImageResource(R.drawable.ic_hidepass);
        }else{
            et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            iv_showpass.setImageResource(R.drawable.ic_eye);
        }

    }

    private void init(){
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        bt_login = findViewById(R.id.bt_login);
        iv_showpass = findViewById(R.id.iv_showpass);
        tv_gotoDangky = findViewById(R.id.tv_gotoDangky);
        fAuth = FirebaseAuth.getInstance();
        pd_loading = new ProgressDialog(LoginActivity.this);
        showPass = false;
    }

    private void onClick(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String pass = et_pass.getText().toString();
                UserLogin(email,pass);
            }
        });

        tv_gotoDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        iv_showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPass = !showPass;
                PasswordStatus();
            }
        });

    }



    private void UserLogin(String email,String password){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
        }else{
            pd_loading.show();
            pd_loading.setMessage("Đang đăng nhập!");
            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fUser = fAuth.getCurrentUser();
                                User u = new User();
                                u.setId(fUser.getUid());
                                u.setEmail(fUser.getEmail());
                                u.setStatus("true");
                                if(!fUser.getEmail().isEmpty()){
                                    SharePreferenceUtil.saveBooleanPereferences(LoginActivity.this, Constant.HAS_LOGIN, true);
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }else{

                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                pd_loading.dismiss();
                            }
                        }
                    });
        }
    }
}
