package com.example.duantn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duantn.R;
import com.example.duantn.activity.model.User;
import com.example.duantn.firebase.MyFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText et_email,et_phone,et_pass,et_repass;
    Button bt_reg;
    ImageView iv_showpass,iv_reshowpass;
    TextView tv_gotoLogin;
    FirebaseAuth fAuth;
    String email,phone,pass,birthday,avatar,indentifyCard,address,status;
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
        fAuth = FirebaseAuth.getInstance();
    }

    private void onClick(){
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String phone = et_phone.getText().toString();
                String pass = et_pass.getText().toString();
                registryUser(email,pass,phone);
            }
        });
    }

    private void registryUser(final String email, final String pass, final String phone) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Sai định dạng mail!");
            et_email.setFocusable(true);
        }else if(pass.length() < 6){
            et_pass.setError("mật khẩu phải hơn 6 ký tự");
            et_pass.setFocusable(true);
        }else{
            fAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser u = fAuth.getCurrentUser();
                        User user = new User(email,pass,phone);

                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email",user.getEmail());
                        hashMap.put("phone", user.getPhoneNumber());

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
                        ref.child(email).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       Toast.makeText(RegisterActivity.this, "Thêm user thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }else{
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
