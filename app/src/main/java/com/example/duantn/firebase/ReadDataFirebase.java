package com.example.duantn.firebase;

import androidx.annotation.NonNull;

import com.example.duantn.activity.model.User;
import com.example.duantn.util.Constant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ReadDataFirebase {
    public static void listenerProductChange(ChildEventListener childEventListener) {
    }
//    public static void listenerFoodChange(ChildEventListener listener){
//        MyFirebase.data.child(Constant.TITLE_FOOD_NODE).addChildEventListener(listener);
//    }
//
//    public static void listenerBillChange(ChildEventListener listener){
//        MyFirebase.data.child(Constant.TITLE_BILL).addChildEventListener(listener);
//    }
//
//    public static void getUserByUserName(String name, TaskGetListener getListener) {
//        MyFirebase.data.child(Constant.TITLE_USER_NODE).orderByChild("userName").equalTo(name).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue() == null) {
//                    getListener.getFail("");
//                } else {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        User user = postSnapshot.getValue(User.class);
//                        getListener.getSuccess(user);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                getListener.getFail(databaseError.toString());
//            }
//        });
//    };
//
//    public interface TaskGetListener{
//        void getSuccess(Object o);
//        void getFail(String e);
//    }
}
