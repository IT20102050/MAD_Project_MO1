package com.example.bowwow;

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

import java.util.ArrayList;

public class allPaymentsActivity extends AppCompatActivity implements PaymentRVAdapter.PaymentClickInterface{

    private RecyclerView paymentRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PaymentRVModel> paymentRVModelArrayList;
    private RelativeLayout bottomSheetRL;
    private PaymentRVAdapter paymentRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_payments);
        paymentRV = findViewById(R.id.idRVPayments);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");
        paymentRVModelArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        paymentRVAdapter = new PaymentRVAdapter(paymentRVModelArrayList, this, this);
        paymentRV.setLayoutManager(new LinearLayoutManager(this));
        paymentRV.setAdapter(paymentRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(allPaymentsActivity.this, AddPaymentActivity.class));
            }
        });

        getAllPayments();
    }

    private void getAllPayments(){
        paymentRVModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                paymentRVModelArrayList.add(snapshot.getValue(com.example.bowwow.PaymentRVModel.class));
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingPB.setVisibility(View.GONE);
                paymentRVAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onPaymentClick(int position) {
        displayBottomSheet(paymentRVModelArrayList.get(position));

    }

    private void displayBottomSheet(PaymentRVModel paymentRVModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog_payment, bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView paymentNameTV = layout.findViewById(R.id.idTVPaymentName);
        TextView paymentCardTV = layout.findViewById(R.id.idTVCard);
        TextView paymentPhoneTV = layout.findViewById(R.id.idTVPhone);
        TextView paymentPlaceTV = layout.findViewById(R.id.idTVPlace);
        TextView paymentAddressTV = layout.findViewById(R.id.idTVAddress);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button submitBtn = layout.findViewById(R.id.idBtnContinue);

        paymentNameTV.setText(paymentRVModel.getPaymentName());
        paymentCardTV.setText(paymentRVModel.getPaymentCard());
        paymentPhoneTV.setText(paymentRVModel.getPaymentPhone());
        paymentPlaceTV.setText(paymentRVModel.getPaymentPlace());
        paymentAddressTV.setText(paymentRVModel.getPaymentAddress());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(allPaymentsActivity.this, EditPaymentActivity.class);
                i.putExtra("Payments", paymentRVModel);
                startActivity(i);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePayment();
                Intent i = new Intent(allPaymentsActivity.this, SuccessfulPage.class);
                startActivity(i);
            }
        });


    }

    private void deletePayment(){
        databaseReference.removeValue();
        Toast.makeText(allPaymentsActivity.this, "Order Successful. Stay Safe..!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogOut:
                Toast.makeText(this, "User Logged Out.", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(allPaymentsActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }



}