package com.example.duantn.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duantn.R;
import com.example.duantn.activity.AddProductActivity;
import com.example.duantn.activity.model.Product;
import com.example.duantn.activity.model.User;
import com.example.duantn.adapter.ProductAdapter;
import com.example.duantn.firebase.ReadDataFirebase;
import com.example.duantn.firebase.WriteDataFirebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    Button btnAdd;
    ProductAdapter productAdapter;
    RecyclerView reProduct;
    List<Product> productList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddProduct();
            }
        });

        reProduct = view.findViewById(R.id.reProduct);
        listenProduct();
        buildReProduct();
        return view;
    }

    private void listenProduct() {
        ReadDataFirebase.listenerProductChange(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product product = dataSnapshot.getValue(Product.class);
                product.key = dataSnapshot.getKey();
                if(product.getUserEmail().equals(User.currentUser.getEmail())){
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
                Product product = dataSnapshot.getValue(Product.class);
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
        productAdapter = new ProductAdapter(productList);
        productAdapter.setProductListener(new ProductAdapter.ProductListener() {
            @Override
            public void editClick(int i) {
                Product product = productList.get(i);
                edit(product);
            }

            @Override
            public void deleteClick(int i) {
                Product product = productList.get(i);
                WriteDataFirebase.deleteProduct(product, new WriteDataFirebase.TaskListener() {
                    @Override
                    public void success() {

                    }

                    @Override
                    public void fail(String e) {

                    }
                });
            }
        });
        reProduct.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        reProduct.setAdapter(productAdapter);
    }

    private void edit(Product product) {
        Intent intent = new Intent(getContext(), AddProductActivity.class);
        intent.putExtra("product", (Serializable) product);
        startActivity(intent);
    }

    private void goToAddProduct() {
        Intent intent = new Intent(getContext(), AddProductActivity.class);
        startActivity(intent);
    }
}
