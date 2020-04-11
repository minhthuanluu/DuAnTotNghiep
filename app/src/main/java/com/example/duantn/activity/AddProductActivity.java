package com.example.duantn.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duantn.R;
import com.example.duantn.activity.model.Category;
import com.example.duantn.activity.model.Product;
import com.example.duantn.firebase.WriteDataFirebase;
import com.example.duantn.fragment.HomeFragment;
import com.example.duantn.fragment.PostFragment;
import com.example.duantn.util.FilePathUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class AddProductActivity extends AppCompatActivity {
    ImageView imv,iv_back;
    EditText edName, edCategory, edPrice, edDescription, edStatus;

    Button btnAdd;
    private Product productEdit;
    boolean isEdit = false;
    FirebaseAuth fAuth;
    Bitmap currentBitmap;
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
        fAuth = FirebaseAuth.getInstance();
        btnAdd = findViewById(R.id.btnAdd);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductActivity.this,MainActivity.class));
            }
        });

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductActivityPermissionsDispatcher.openGalleryWithPermissionCheck(AddProductActivity.this);
            }
        });

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
            Picasso.with(this).load(productEdit.getImageLink()).into(imv);
        }

    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        try {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 000);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            currentBitmap = BitmapFactory.decodeFile(FilePathUtils.getPath(AddProductActivity.this, data.getData()));
            imv.setImageBitmap(currentBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AddProductActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void edit() {
        final String name = edName.getText().toString();
        final String category = edCategory.getText().toString();
        final double price = Double.parseDouble(edPrice.getText().toString());
        final String des = edDescription.getText().toString();
        final String status = edStatus.getText().toString();
        final Product product = new Product(name, category, productEdit.getImageLink(), price, des, status, fAuth.getCurrentUser().getEmail());
        product.key = productEdit.key;


        // Nếu bitmap bị null thì sẽ lấy link củ của product, còn nếu có bitmap thì upload lên sau đó lấy link đó add vào firebase
        if(currentBitmap == null){
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
        }else {
            WriteDataFirebase.uploadImage(currentBitmap, name, new WriteDataFirebase.UploadListener() {
                @Override
                public void success(String path) {
                    Product product = new Product(name, category, path, price, des, status, fAuth.getCurrentUser().getEmail());
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

                @Override
                public void fail(String error) {

                }
            });
        }
    }

    private void addProduct() {
        final String name = edName.getText().toString();
        final String category = edCategory.getText().toString();
        final double price = Double.parseDouble(edPrice.getText().toString());
        final String des = edDescription.getText().toString();
        final String status = edStatus.getText().toString();

        if(currentBitmap == null){
            currentBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        }
        WriteDataFirebase.uploadImage(currentBitmap, name, new WriteDataFirebase.UploadListener() {
            @Override
            public void success(String path) {
                Product product = new Product(name, category, path, price, des, status, fAuth.getCurrentUser().getEmail());
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

            @Override
            public void fail(String error) {

            }
        });

    }
}
