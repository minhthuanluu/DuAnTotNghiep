package com.example.duantn.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duantn.R;
import com.example.duantn.activity.model.Product;
import com.example.duantn.activity.model.User;
import com.example.duantn.firebase.WriteDataFirebase;

public class AddProductActivity extends AppCompatActivity {
    ImageView imv;
    EditText edName, edCategory, edPrice, edDescription, edStatus;

    Button btnAdd;
    private Product productEdit;
    boolean isEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if(getIntent().getExtras() != null){
            productEdit = (Product) getIntent().getExtras().getSerializable("product");
            isEdit = true;
        }

        edName = findViewById(R.id.edName);
        edCategory = findViewById(R.id.edCategory);
        edPrice = findViewById(R.id.edPrice);
        edDescription = findViewById(R.id.edDescription);
        edStatus = findViewById(R.id.edStatus);
        imv = findViewById(R.id.imv);

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    edit();
                }else {
                    addProduct();
                }
            }
        });

        if(isEdit){
            edName.setText(productEdit.getName());
            edCategory.setText(productEdit.getCategory());
            edPrice.setText(productEdit.getPrice() + "");
            edDescription.setText(productEdit.getDescription());
            edStatus.setText(productEdit.getStatus());
            btnAdd.setText("edit");
        }

    }

    private void edit() {
        String name = edName.getText().toString();
        String category = edCategory.getText().toString();
        double price = Double.parseDouble(edPrice.getText().toString());
        String des = edDescription.getText().toString();
        String status = edStatus.getText().toString();
        Product product = new Product(name, category, "test.png", price, des, status, User.currentUser.getEmail());
        product.key = productEdit.key;

        WriteDataFirebase.editProduct(product, new WriteDataFirebase.TaskListener() {
            @Override
            public void success() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void fail(String e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddProductActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void addProduct() {
        String name = edName.getText().toString();
        String category = edCategory.getText().toString();
        double price = Double.parseDouble(edPrice.getText().toString());
        String des = edDescription.getText().toString();
        String status = edStatus.getText().toString();
        Product product = new Product(name, category, "test.png", price, des, status, User.currentUser.getEmail());

        WriteDataFirebase.addProduct(product, new WriteDataFirebase.TaskListener() {
            @Override
            public void success() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void fail(String e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddProductActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
