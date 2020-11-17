package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class ForgotPassword extends AppCompatActivity {
    private String role;
    private String phoneNumber;
    private Button resetbutton;
    private EditText newPassword;
    private EditText confirmPassword;
    boolean corr=true;
    boolean corrN=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Intent i=getIntent();
        role=i.getStringExtra("role");
        phoneNumber=i.getStringExtra("phone");
        newPassword=findViewById(R.id.newPasswordForgot);
        confirmPassword=findViewById(R.id.confirmPasswordForgot);
        resetbutton=findViewById(R.id.resetPasswordButton);
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()<6)
                    {
                        corrN=false;
                        findViewById(R.id.passwordLengthWrongForgot).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        corrN=true;
                        findViewById(R.id.passwordLengthWrongForgot).setVisibility(View.INVISIBLE);
                    }
                }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(corrN==true)
                {
                    if(confirmPassword.getText().toString().equals(newPassword.getText().toString()))
                    {findViewById(R.id.resetPasswordButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.confirmPasswordWrongForgot).setVisibility(View.INVISIBLE);
                        findViewById(R.id.resetPasswordButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(role.equals("HR"))
                                {
                                    FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(phoneNumber)
                                            .child("Password")
                                            .setValue(newPassword.getText().toString());
                                }
                                else
                                {
                                    FirebaseDatabase.getInstance().getReference().child("Employees")
                                            .child(phoneNumber)
                                            .child("Password")
                                            .setValue(newPassword.getText().toString());
                                }
                                Toast.makeText(ForgotPassword.this, "Your password has been reset!!" , Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),PasswordActivity.class);
                                i.putExtra("phone",phoneNumber);
                                i.putExtra("role",role);
                                startActivity(i);
                            }
                        });}
                    else
                    {
                        findViewById(R.id.confirmPasswordWrongForgot).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}