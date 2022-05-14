package com.example.bowwownew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    private TextInputEditText productNameEdt, productPriceEdt, productImgEdt, productCatEdt;
    private Button addProductBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String productID;

    //this is the all products page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productNameEdt = findViewById(R.id.idEdtProductName);
        productPriceEdt = findViewById(R.id.idEdtProductPrice);
        productImgEdt = findViewById(R.id.idEdtProductImageLink);
        productCatEdt = findViewById(R.id.idEdtProductCat);
        addProductBtn = findViewById(R.id.idBtnAddProduct);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String productName = productNameEdt.getText().toString();
                String productPrice = productPriceEdt.getText().toString();
                String productImg = productImgEdt.getText().toString();
                String productCat = productCatEdt.getText().toString();
                productID = productName;
                ProductRVModal productRVModal = new ProductRVModal(productName, productCat, productPrice, productImg, productID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(productID).setValue(productRVModal);
                        Toast.makeText(AddProductActivity.this, "Product Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductActivity.this,MainActivity.class));
                        
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddProductActivity.this, "Error is" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}