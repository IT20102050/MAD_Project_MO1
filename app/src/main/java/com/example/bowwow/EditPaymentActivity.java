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

public class EditPaymentActivity extends AppCompatActivity {

    private TextInputEditText paymentNameEdt, paymentPhoneEdt, paymentAddressEdt, paymentPlaceEdt, paymentCardEdt, paymentNoteEdt;
    private Button updatePaymentBtn, deletePaymentBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String paymentID;
    private PaymentRVModel paymentRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        paymentNameEdt = findViewById(R.id.idEdtPaymentName);
        paymentPhoneEdt = findViewById(R.id.idEdtPaymentPhone);
        paymentAddressEdt = findViewById(R.id.idEdtPaymentAddress);
        paymentPlaceEdt = findViewById(R.id.idEdtPaymentPlace);
        paymentCardEdt = findViewById(R.id.idEdtPaymentCard);
        paymentNoteEdt = findViewById(R.id.idEdtPaymentNote);
        updatePaymentBtn = findViewById(R.id.idBtnUpdatePayment);
        deletePaymentBtn = findViewById(R.id.idBtnDeletePayment);
        loadingPB = findViewById(R.id.idPBLoading);

        paymentRVModel = getIntent().getParcelableExtra("Payments");
        if (paymentRVModel != null) {
            paymentNameEdt.setText(paymentRVModel.getPaymentName());
            paymentPhoneEdt.setText(paymentRVModel.getPaymentPhone());
            paymentAddressEdt.setText(paymentRVModel.getPaymentAddress());
            paymentPlaceEdt.setText(paymentRVModel.getPaymentPlace());
            paymentCardEdt.setText(paymentRVModel.getPaymentCard());
            paymentNoteEdt.setText(paymentRVModel.getPaymentNote());
            paymentID = paymentRVModel.getPaymentId();
        }

        databaseReference = firebaseDatabase.getReference("Payments").child(paymentID);
        updatePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String paymentName = paymentNameEdt.getText().toString();
                String paymentPhone = paymentPhoneEdt.getText().toString();
                String paymentAddress = paymentAddressEdt.getText().toString();
                String paymentPlace = paymentPlaceEdt.getText().toString();
                String paymentCard = paymentCardEdt.getText().toString();
                String paymentNote = paymentNoteEdt.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("paymentName", paymentName);
                map.put("paymentPhone", paymentPhone);
                map.put("paymentAddress", paymentAddress);
                map.put("paymentPlace", paymentPlace);
                map.put("paymentCard", paymentCard);
                map.put("paymentNote", paymentNote);
                map.put("paymentId", paymentID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        startActivity(new Intent(EditPaymentActivity.this,com.example.bowwow.allPaymentsActivity.class));
                        Toast.makeText(EditPaymentActivity.this, "Payment Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPaymentActivity.this, "Fail Update Payment", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        deletePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePayment();
            }
        });

    }

    private void deletePayment(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Payments");
        databaseReference.removeValue();
        Toast.makeText(EditPaymentActivity.this, "Payment Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPaymentActivity.this,com.example.bowwow.allPaymentsActivity.class));

    }
}