package com.example.duantn.firebase;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duantn.activity.model.Product;
import com.example.duantn.util.Constant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class WriteDataFirebase {
    public static final String TAG = "writeDataFirebase";

    public static void addProduct(Product product, final TaskListener listerner){
        MyFirebase.data.child("product").push().setValue(product, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseReference == null){
                    listerner.fail(databaseError.getDetails());
                }else {
                    listerner.success();
                }
            }
        });
    }
//
    public static void editProduct(Product product, final TaskListener listener){
        MyFirebase.data.child("product").child(product.key).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.success();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.fail(e.toString());
                    }
                });
    }
//
//    public static void addBill(Bill bill, MyFirebase.TaskListener listerner){
//        MyFirebase.data.child(Constant.TITLE_BILL).push().setValue(bill, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                if(databaseReference == null){
//                    listerner.fail(databaseError.getDetails());
//                }else {
//                    listerner.success();
//                }
//            }
//        });
//    }
//
//    public static void editStateBill(Bill bill, MyFirebase.TaskListener listener){
//        MyFirebase.data.child(Constant.TITLE_BILL).child(bill.key).setValue(bill)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        listener.success();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        listener.fail(e.toString());
//                    }
//                });
//    }
//
//    public static void uploadImage(Bitmap bitmap, String title, UploadListener uploadListener){
//        StorageReference storageRef = MyFirebase.storage.getReference();
//        StorageReference mountainsRef = storageRef.child(title);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//                return mountainsRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                    uploadListener.success(downloadUri.toString());
//                } else {
//                    uploadListener.fail("error, try again");
//                }
//            }
//        });
//    }
//
    public static void deleteProduct(Product product, final TaskListener listener){
        MyFirebase.data.child("product").child(product.key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    listener.success();
                }else {
                    listener.fail("can't delete this food now, try latter!");
                }
            }
        });
    }
//
//    public interface UploadListener{
//        void success(String path);
//        void fail(String error);
//    }

    public interface TaskListener{
        void success();
        void fail(String e);
    }
}
