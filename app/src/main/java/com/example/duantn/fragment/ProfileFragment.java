package com.example.duantn.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duantn.R;
import com.example.duantn.activity.EditProfileActivity;
import com.example.duantn.activity.LoginActivity;
import com.example.duantn.activity.model.User;
import com.example.duantn.firebase.MyFirebase;
import com.example.duantn.util.Constant;
import com.example.duantn.util.SharePreferenceUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    User currentUser;
    ProgressDialog pd_loading;
    CircleImageView imvAvatar;
    ImageView iv_status;
    TextView tvName, tvAddress,tv_sua;
    Button bt_logout;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        imvAvatar = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        bt_logout = view.findViewById(R.id.bt_logout);
        tv_sua = view.findViewById(R.id.tv_sua);
        iv_status = view.findViewById(R.id.iv_status);
        pd_loading = new ProgressDialog(getContext());

        getCurrentUser();

        tv_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean hasLogin = SharePreferenceUtil.getBooleanPerferences(getContext(), Constant.HAS_LOGIN, false);
//
//                if(hasLogin){
//                    getCurrentUser();
//                }else {
//                    goToLogin();
//                }
                goToLogin();
            }
        });

        return view;
    }

    private void goToLogin() {
        pd_loading.show();
        pd_loading.setMessage("Đang đăng xuất!");
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        pd_loading.hide();
                    }
                },
                1000);
    }


    private void getCurrentUser() {
        String email = "test";
        MyFirebase.getUserByEmail(email, new MyFirebase.TaskListener() {
            @Override
            public void fail(String error) {

            }

            @Override
            public void success(User user) {
                currentUser = user;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        setUpInfo();
                    }
                });
            }
        });
    }

    private void setUpInfo() {
        tvName.setText(currentUser.getEmail());
        tvAddress.setText(currentUser.getAddress());
        Picasso.with(getContext()).load(currentUser.getAvatar());
    }


}
