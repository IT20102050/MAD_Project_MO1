package com.example.bowwownew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessfulPage extends AppCompatActivity {

    private Button idBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_page);
        idBackHome = findViewById(R.id.idBackHome);

        //onclicklistener for idbackhome
        idBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuccessfulPage.this, UserViewCategories.class);
            }
        });
    }
}