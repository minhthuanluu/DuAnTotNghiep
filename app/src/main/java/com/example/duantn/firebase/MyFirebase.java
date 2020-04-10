package com.example.duantn.firebase;

import androidx.annotation.NonNull;

import com.example.duantn.activity.model.User;
import com.example.duantn.util.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MyFirebase {
    public static final String TAG = "myFireBase";
    public static DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static User userLog;

//    public static void checkLogin(final String name, final String pass, final TaskListener listener){
//        data.child("user").orderByChild("userName").equalTo(name).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getValue() == null){
//                    listener.fail("Can't find " + name);
//                }else {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
//                    {
//                        User user = postSnapshot.getValue(User.class);
//                        if(user.getPassword().equals(pass)){
//                            userLog = user;
//                            listener.success();
//                        }else {
//                            listener.fail("password wrong");
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                listener.fail(databaseError.toString());
//            }
//        });
//    }

    public static void getUserByEmail(final String email, final TaskListener listener){
        data.child("user").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    listener.fail("Can't find " + email);
                }else {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        User user = postSnapshot.getValue(User.class);
                        userLog = user;
                        listener.success(user);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.fail(databaseError.toString());
            }
        });
    }

    public static void addUser(User user, DatabaseReference.CompletionListener listner){
        data.child("user").push().setValue(user, listner);
    }

    public static void logout(){
        getUserLogin().setStatus("false");
    }

    public static User getUserLogin(){
        return userLog;
    }

    public interface TaskListener {
        void fail(String error);
        void success(User user);
    }
}
