package com.example.duantn.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duantn.R;
import com.example.duantn.activity.model.User;
import com.example.duantn.firebase.MyFirebase;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    User currentUser;

    CircleImageView imvAvatar;
    TextView tvName, tvAddress;

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
        getCurrentUser();
        return view;
    }

    private void getCurrentUser() {
        String email = "test";
        MyFirebase.getUserByEmail(email, new MyFirebase.TaskListener() {
            @Override
            public void fail(String error) {
                Log.i("TAG", "fail: ");
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
    }
}
