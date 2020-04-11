package com.example.duantn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    ProgressDialog pd;
    FirebaseAuth fAuth;
    boolean showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        onClick();

    }

    private void checkDuplicatePass() {
        String repass = et_repass.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone.getText().toString();
        String pass = et_pass.getText().toString();
        if(pass.equals(repass)){
            registryUser(email,pass,phone);
        }else{
            Toast.makeText(this, "Mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_pass = findViewById(R.id.et_pass);
        et_repass = findViewById(R.id.et_repass);
        tv_gotoLogin = findViewById(R.id.tv_gotoLogin);
        iv_showpass = findViewById(R.id.iv_showpass);
        iv_reshowpass = findViewById(R.id.iv_reshowpass);
        pd = new ProgressDialog(RegisterActivity.this);
        bt_reg = findViewById(R.id.bt_reg);
        fAuth = FirebaseAuth.getInstance();
    }

    private void onClick(){
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDuplicatePass();

            }
        });
        iv_showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPass = !showPass;
                PasswordStatus(et_pass,iv_showpass);
            }
        });
        iv_reshowpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPass = !showPass;
                PasswordStatus(et_repass,iv_reshowpass);
            }
        });
    }


    private void PasswordStatus(EditText et_pass,ImageView iv_showpass) {
        if(showPass==true){
            et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            iv_showpass.setImageResource(R.drawable.ic_hidepass);
        }else{
            et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            iv_showpass.setImageResource(R.drawable.ic_eye);
        }

    }

    private void registryUser(final String email, final String pass, final String phone) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Sai định dạng mail!", Toast.LENGTH_SHORT).show();
            et_email.setFocusable(true);
        }else if(pass.length() < 6){
            Toast.makeText(this, "Mật khẩu phải hơn 6 ký tự", Toast.LENGTH_SHORT).show();
            et_pass.setFocusable(true);
        }else{
            checkDuplicatePass();
            pd.setMessage("Tiến hành đăng ký");
            pd.show();
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
                        ref.child(u.getUid()).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       Toast.makeText(RegisterActivity.this, "Thêm user thành công!", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
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
