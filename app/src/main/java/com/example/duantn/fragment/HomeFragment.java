package com.example.duantn.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.duantn.R;
import com.example.duantn.activity.AddProductActivity;
import com.example.duantn.activity.EditProductActivity;
import com.example.duantn.activity.EditProfileActivity;
import com.example.duantn.activity.MainActivity;
import com.example.duantn.activity.model.Product;
import com.example.duantn.activity.model.User;
import com.example.duantn.adapter.ProductAdapter;
import com.example.duantn.firebase.ReadDataFirebase;
import com.example.duantn.firebase.WriteDataFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public HomeFragment() {
    }

    ProductAdapter productAdapter;
    RecyclerView reProduct;
    User currentUser;
    Product product;
    DatabaseReference reference;
    ProgressDialog pd;
    List<Product> productList = new ArrayList<Product>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        reProduct = view.findViewById(R.id.reProduct);
        pd = new ProgressDialog(getContext());
        listenProduct();
        getProduct();
        buildReProduct();
        return view;
    }


    private void listenProduct() {
        ReadDataFirebase.listenerProductChange(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                productList.clear();
                Product product = dataSnapshot.getValue(Product.class);
                product.key = dataSnapshot.getKey();
                if(product.getUserEmail().equals(currentUser.getEmail())){
                    productList.add(product);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            productAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                productList.clear();
                Product product = dataSnapshot.getValue(Product.class);
                product.key = dataSnapshot.getKey();
                for (int i = 0; i < productList.size(); i++) {
                    if(productList.get(i).key.equals(product.key)){
                        productList.set(i, product);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                productAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                product = dataSnapshot.getValue(Product.class);
                product.key = dataSnapshot.getKey();
                for (int i = 0; i < productList.size(); i++) {
                    if(productList.get(i).key.equals(product.key)){
                        productList.remove(i);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                productAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void buildReProduct() {
        productList.clear();
        productAdapter = new ProductAdapter(productList);
        productAdapter.setProductListener(new ProductAdapter.ProductListener() {
            @Override
            public void editClick(int i) {
                edit(product);
            }

            @Override
            public void deleteClick(int i) {

            }
        });
        reProduct.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        reProduct.setAdapter(productAdapter);
    }

    private void edit(Product product) {
        Intent intent = new Intent(getActivity().getBaseContext(), EditProductActivity.class);
        intent.putExtra("key", product);
        getActivity().startActivity(intent);
    }

//    private void goToAddProduct() {
//        Intent intent = new Intent(getContext(), AddProductActivity.class);
//        startActivity(intent);
//    }

    private void getProduct(){
        pd.show();
        pd.setMessage("Loading");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("product");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();

                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Product post = item.getValue(Product.class);
                    productList.add(post);
                    productAdapter.notifyDataSetChanged();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("a", "Failed to read value.", error.toException());
            }
        });
    }
}
