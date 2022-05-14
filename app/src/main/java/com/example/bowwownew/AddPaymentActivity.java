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

public class AddPaymentActivity extends AppCompatActivity {

    private TextInputEditText paymentNameEdt, paymentPhoneEdt, paymentAddressEdt, paymentPlaceEdt, paymentCardEdt, paymentNoteEdt;
    private Button addPaymentBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        paymentNameEdt = findViewById(R.id.idEdtPaymentName);
        paymentPhoneEdt = findViewById(R.id.idEdtPaymentPhone);
        paymentAddressEdt = findViewById(R.id.idEdtPaymentAddress);
        paymentPlaceEdt = findViewById(R.id.idEdtPaymentPlace);
        paymentCardEdt = findViewById(R.id.idEdtPaymentCard);
        paymentNoteEdt = findViewById(R.id.idEdtPaymentNote);
        addPaymentBtn = findViewById(R.id.idBtnAddPayment);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Payments");

        addPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paymentName = paymentNameEdt.getText().toString();
                String paymentPhone = paymentPhoneEdt.getText().toString();
                String paymentAddress = paymentAddressEdt.getText().toString();
                String paymentPlace = paymentPlaceEdt.getText().toString();
                String paymentCard = paymentCardEdt.getText().toString();
                String paymentNote = paymentNoteEdt.getText().toString();
                paymentID = paymentCard;
                com.example.bowwownew.PaymentRVModel paymentRVModel = new com.example.bowwownew.PaymentRVModel(paymentName,paymentPhone,paymentAddress,paymentPlace,paymentCard,paymentNote, paymentID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(paymentID).setValue(paymentRVModel);
                        Toast.makeText(AddPaymentActivity.this, "Payment Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPaymentActivity.this,com.example.bowwownew.allPaymentsActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPaymentActivity.this, "Error is"+toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}