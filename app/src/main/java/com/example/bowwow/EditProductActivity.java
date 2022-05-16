package com.example.bowwow;

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

import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {

    private TextInputEditText productNameEdt, productPriceEdt, productImgEdt, productCatEdt;
    private Button updateProductBtn, deleteProductBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String productID;
    private ProductRVModal productRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        firebaseDatabase = FirebaseDatabase.getInstance();
        productNameEdt = findViewById(R.id.idEdtProductName);
        productPriceEdt = findViewById(R.id.idEdtProductPrice);
        productImgEdt = findViewById(R.id.idEdtProductImageLink);
        productCatEdt = findViewById(R.id.idEdtProductCat);
        updateProductBtn = findViewById(R.id.idBtnUpdateProduct);
        deleteProductBtn = findViewById(R.id.idBtnDeleteProduct);
        loadingPB = findViewById(R.id.idPBLoading);
        productRVModal = getIntent().getParcelableExtra("product");
        if (productRVModal!=null){
            productNameEdt.setText(productRVModal.getProductName());
            productPriceEdt.setText(productRVModal.getProductPrice());
            productImgEdt.setText(productRVModal.getProductImg());
            productCatEdt.setText(productRVModal.getProductCategory());
            productID = productRVModal.getProductID();
        }


        databaseReference = firebaseDatabase.getReference("Products").child(productID);
        updateProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String productName = productNameEdt.getText().toString();
                String productPrice = productPriceEdt.getText().toString();
                String productImg = productImgEdt.getText().toString();
                String productCat = productCatEdt.getText().toString();


                Map<String,Object> map = new HashMap<>();
                map.put("productName", productName);
                map.put("productPrice", productPrice);
                map.put("productImg", productImg);
                map.put("productCategory", productCat);
                map.put("productID", productID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);

                        startActivity(new Intent(EditProductActivity.this,MainActivity.class));
                        Toast.makeText(EditProductActivity.this, "Product Updated..", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditProductActivity.this, "Fail to update course..", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });

    }

    private void deleteProduct(){
        databaseReference.removeValue();
        startActivity(new Intent(EditProductActivity.this,MainActivity.class));
        Toast.makeText(this, "Product Deleted..", Toast.LENGTH_SHORT).show();


    }
}