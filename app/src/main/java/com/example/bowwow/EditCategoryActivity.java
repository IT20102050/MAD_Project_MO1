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

public class EditCategoryActivity extends AppCompatActivity {

    private TextInputEditText categoryNameEdt,categoryDesEdt,categoryDateEdt,categoryImgEdt;
    private Button updateCategoryBtn,deleteCategoryBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String categoryID;
    private CategoryRVModel categoryRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        firebaseDatabase = FirebaseDatabase.getInstance();
        categoryNameEdt = findViewById(R.id.idEdtCategoryName);
        categoryDesEdt = findViewById(R.id.idEdtCategoryDescription);
        categoryDateEdt = findViewById(R.id.idEdtCategoryDate);
        categoryImgEdt = findViewById(R.id.idEdtCategoryImageLink);
        updateCategoryBtn = findViewById(R.id.idBtnUpdateCategory);
        deleteCategoryBtn = findViewById(R.id.idBtnDeleteCategory);
        loadingPB = findViewById(R.id.idpBLoading);
        categoryRVModel = getIntent().getParcelableExtra("category");
        if (categoryRVModel!=null){
            categoryNameEdt.setText(categoryRVModel.getCategoryName());
            categoryDesEdt.setText(categoryRVModel.getCategoryDescription());
            categoryDateEdt.setText(categoryRVModel.getCategoryDescription());
            categoryImgEdt.setText(categoryRVModel.getCategoryImage());
            categoryID = categoryRVModel.getCategoryID();
        }

        databaseReference = firebaseDatabase.getReference("Categories").child(categoryID);
        updateCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String categoryName = categoryNameEdt.getText().toString();
                String categoryDescription = categoryDesEdt.getText().toString();
                String categoryDate = categoryDateEdt.getText().toString();
                String categoryImage = categoryImgEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("categoryName",categoryName);
                map.put("categoryDescription",categoryDescription);
                map.put("categoryDate",categoryDate);
                map.put("categoryImage",categoryImage);
                map.put("categoryID",categoryID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);

                        startActivity(new Intent(EditCategoryActivity.this,MainActivity.class));
                        Toast.makeText(EditCategoryActivity.this, "Category Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCategoryActivity.this, "Fail Update Category", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //**********************************//

        deleteCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategory();
            }
        });

    }

    private void deleteCategory(){
        databaseReference.removeValue();
        startActivity(new Intent(EditCategoryActivity.this,MainActivity.class));
        Toast.makeText(this, "Category Deleted", Toast.LENGTH_SHORT).show();
    }
}