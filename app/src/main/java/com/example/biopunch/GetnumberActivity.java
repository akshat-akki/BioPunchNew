package com.example.biopunch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetnumberActivity extends AppCompatActivity {
    String mobile;
    private EditText editTextMobile;
    ProgressBar progressBar;
    public void numberHR()
    {
        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( s.length() == 10)
                    findViewById(R.id.buttonnext).setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.buttonnext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) getSystemService(GetnumberActivity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                progressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.buttonnext).setVisibility(View.INVISIBLE); mobile = editTextMobile.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                    //
                }
                mobile="+91"+mobile;
                final String userName =mobile;
                FirebaseDatabase.getInstance().getReference().child("users").orderByChild("phone").equalTo(userName).addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //Log.i(Constants.TAG, "dataSnapshot value = " + dataSnapshot.getValue());
                                if (dataSnapshot.exists()) {
                                    //check if it is an hr number or employee number
                                    //dialog alert if employee number
                                    Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                                    intent.putExtra("phone",mobile);
                                    //intent.putExtra()-hr hai ye pass kardo
                                    startActivity(intent);
                                    // User Exists
                                    // Do your stuff here if user already exist
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.putExtra("mobile", mobile);
                                    //hr hai pass kardo
                                    startActivity(intent);

                                    // User Not Yet Exists
                                    // Do your stuff here if user not yet exists
                                }
                            }
                            @Override
                            public void onCancelled (DatabaseError databaseError){

                            }
                        }

                );

            }
        });
    }
    public void numberEmployee()
    {
        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( s.length() == 10)
                    findViewById(R.id.buttonnext).setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.buttonnext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) getSystemService(GetnumberActivity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                progressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.buttonnext).setVisibility(View.INVISIBLE);
                mobile = editTextMobile.getText().toString().trim();
                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                    //
                }
                    mobile="+91"+mobile;
                    final String userName =mobile;
                    FirebaseDatabase.getInstance().getReference().child("Employees").orderByChild("MyNo").equalTo(mobile).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    //Log.i(Constants.TAG, "dataSnapshot value = " + dataSnapshot.getValue());
                                    if (dataSnapshot.exists()) {

                                                //check if it is an hr number or employee number
                                                //return to previous activity if hr no
                                                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                                                intent.putExtra("phone", mobile);
                                                //employee h pass kardo
                                                startActivity(intent);
                                                // User Exists
                                                // Do your stuff here if user already exist
                                            }


                                        else {
                                        //if number is not registered
                                        new AlertDialog.Builder(GetnumberActivity.this).setIcon(android.R.drawable.stat_notify_error)
                                                .setTitle("Error!")
                                                .setMessage("The number you entered is not registered with any of the company.Check your number again or contact your company with login credentials.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                       // Intent ip=new Intent(getApplicationContext(),GetnumberActivity.class);
                                                        //ip.putExtra("login","employee");
                                                        //startActivity(ip);
                                                    }
                                                })
                                                .show();
                                    }
                                }
                                @Override
                                public void onCancelled (DatabaseError databaseError){

                                }
                            }

                    );

                }
            });
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getnumber);
        Intent i=getIntent();
        String person=i.getStringExtra("login");
        Toast.makeText(this, person, Toast.LENGTH_SHORT).show();
        editTextMobile = findViewById(R.id.editTextPhone);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonnext).setVisibility(View.INVISIBLE);
        if(person.equals("HR"))
            numberHR();
        else
            numberEmployee();
    }
    }
