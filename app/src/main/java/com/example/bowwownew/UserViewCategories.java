package com.example.bowwownew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserViewCategories extends AppCompatActivity implements  CategoryRVAdapter.CategoryClickInterface{

    private RecyclerView categoryRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private RelativeLayout bottomSheetRL;
    private CategoryRVAdapter categoryRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_categories);

        categoryRV = findViewById(R.id.idRvCategories);
        loadingPB = findViewById(R.id.idpBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Categories");
        categoryRVModelArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList,this,this);
        categoryRV.setLayoutManager(new LinearLayoutManager(this));
        categoryRV.setAdapter(categoryRVAdapter);



        getAllCategories();


    }

    private void getAllCategories(){
        categoryRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                loadingPB.setVisibility(View.GONE);
                categoryRVModelArrayList.add(snapshot.getValue(CategoryRVModel.class));
                categoryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String s) {
                loadingPB.setVisibility(View.GONE);
                categoryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot sapshot) {
                loadingPB.setVisibility(View.GONE);
                categoryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable String s) {
                loadingPB.setVisibility(View.GONE);
                categoryRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCategoryClick(int position1) {
        displayBottomSheet(categoryRVModelArrayList.get(position1));

    }
    private void displayBottomSheet(CategoryRVModel categoryRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.user_cat_bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView categoryNameTV = layout.findViewById(R.id.idTVCategoryName);
        TextView categoryDescTV = layout.findViewById(R.id.idTVDescription);
        TextView categoryDateTV = layout.findViewById(R.id.idTVDate);
        ImageView categoryIV = layout.findViewById(R.id.idTVCategory);
        Button ViewItems = layout.findViewById(R.id.idBtnItems);



        categoryNameTV.setText(categoryRVModel.getCategoryName());
        categoryDescTV.setText(categoryRVModel.getCategoryDescription());
        categoryDateTV.setText(categoryRVModel.getCategoryDate());
        Picasso.get().load(categoryRVModel.getCategoryImage()).into(categoryIV);



        ViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserViewCategories.this, UserViewItems.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogOut:
                Toast.makeText(this, "User logged out", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent (UserViewCategories.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


}