package com.example.duantn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        onClick();
    }

    private void init(){
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        bt_login = findViewById(R.id.bt_login);
        fAuth = FirebaseAuth.getInstance();
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
    }

    private void UserLogin(String email,String password){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Empty fields", Toast.LENGTH_SHORT).show();
        }else{
            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fUser = fAuth.getCurrentUser();

                                User u = new User();
                                u.setId(fUser.getUid());
                                u.setEmail(fUser.getEmail());
                                SharePreferenceUtil.saveBooleanPereferences(LoginActivity.this, Constant.HAS_LOGIN, true);

                                Toast.makeText(LoginActivity.this, Constant.HAS_LOGIN+"", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
