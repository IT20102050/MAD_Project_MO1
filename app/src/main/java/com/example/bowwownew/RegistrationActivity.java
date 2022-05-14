package com.example.bowwownew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPassword;
    Button regBtn,regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//hooks

        regName=findViewById(R.id.reg_name);
        regUsername=findViewById(R.id.reg_username);
        regEmail=findViewById(R.id.reg_email);
        regPhoneNo=findViewById(R.id.reg_phoneNo);
        regPassword=findViewById(R.id.reg_password);
        regBtn=findViewById(R.id.reg_Btn);
        regToLoginBtn=findViewById(R.id.reg_login_btn);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode= FirebaseDatabase.getInstance();
                reference= rootNode.getReference("users");

                String name =regName.getEditText().getText().toString();
                String username =regUsername.getEditText().getText().toString();
                String email =regEmail.getEditText().getText().toString();
                String phoneNo =regPhoneNo.getEditText().getText().toString();
                String password =regPassword.getEditText().getText().toString();

                if(!validateName() | !validateEmail() | !validateUsername() | !validatePhoneNumber() | !validatePassword()){
                    return;
                }else {
                    UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                    reference.child(username).setValue(helperClass);

                    //Intent intent =  new Intent(getApplicationContext(),VerifyPhoneNo.class);
                    //intent.putExtra("phoneNo",phoneNo);
                    //startActivity(intent);

                    Toast.makeText(RegistrationActivity.this, "You Registered Successfully..!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                }
            }
        });

    }
    private boolean validateName(){
        String val =regName.getEditText().getText().toString();

        if(val.isEmpty()){
            regName.setError("Field Cannot be empty");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false );
            return true;
        }
    }
    private boolean validateUsername(){
        String val =regUsername.getEditText().getText().toString();
        String noWhiteSpace ="\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            regUsername.setError("Field Cannot be empty");
            return false;
        }else if(val.length()>=15){
            regUsername.setError("User Name too Long");
            return false;
        } else if (!val.matches(noWhiteSpace)){
            regUsername.setError("Spaces Not Allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val =regEmail.getEditText().getText().toString();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Field Cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }
    }
    private boolean validatePhoneNumber(){
        String val =regPhoneNo.getEditText().getText().toString();

        if(val.isEmpty()){
            regPhoneNo.setError("Field Cannot be empty");
            return false;
        }else {
            regPhoneNo.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String val =regPassword.getEditText().getText().toString();
        if(val.isEmpty()){
            regPassword.setError("Field Cannot be empty");
            return false;
        }
        else {
            regPassword.setError(null);
            return true;
        }
    }




}
