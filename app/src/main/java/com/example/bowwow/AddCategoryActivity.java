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

public class AddCategoryActivity extends AppCompatActivity {

    private TextInputEditText categoryNameEdt, categoryDesEdt, categoryDateEdt, categoryImgEdt;
    private Button addCategoryBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryNameEdt = findViewById(R.id.idEdtCategoryName);
        categoryDesEdt = findViewById(R.id.idEdtCategoryDescription);
        categoryDateEdt = findViewById(R.id.idEdtCategoryDate);
        categoryImgEdt = findViewById(R.id.idEdtCategoryImageLink);
        addCategoryBtn = findViewById(R.id.idBtnAddCategory);
        loadingPB = findViewById(R.id.idpBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Categories");

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String categoryName = categoryNameEdt.getText().toString();
                String categoryDescription = categoryDesEdt.getText().toString();
                String categoryDate = categoryDateEdt.getText().toString();
                String categoryImage = categoryImgEdt.getText().toString();
                categoryID = categoryName;
                com.example.bowwow.CategoryRVModel categoryRVModel = new com.example.bowwow.CategoryRVModel(categoryName, categoryDescription, categoryDate, categoryImage, categoryID);

                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(categoryID).setValue(categoryRVModel);
                        Toast.makeText(AddCategoryActivity.this, "Category Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCategoryActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCategoryActivity.this, "Error is" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}