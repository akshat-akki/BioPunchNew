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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class GetnumberActivity extends AppCompatActivity {
    String mobile;
    private EditText editTextMobile;
    ProgressBar progressBar;
    int empflag=0;
    int intentflag=0;
    String countryCode="+91";
    CountryCodePicker countryCodePicker;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(GetnumberActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to exit?")
                .setPositiveButton("No", null)
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();

                        System.exit(0);
                    }
                }).show();
    }

    private void numberHR()
    {
        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( s.length() > 1 )
                {

                    findViewById(R.id.buttonNext).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) getSystemService(GetnumberActivity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                progressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
                mobile = editTextMobile.getText().toString().trim();
                if(mobile.isEmpty() ){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;

                }
                mobile=countryCode+mobile;
                final String userName =mobile;
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersdRef = rootRef.child("Employees");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.child("MyNo").getValue(String.class).equals(userName)) {
                                empflag = 1;
                                new AlertDialog.Builder(GetnumberActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Error")
                                        .setMessage("You are registered as an employee")
                                        .setPositiveButton("Enter as an employee", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), GetnumberActivity.class);
                                                intent.putExtra("login","employee");
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), RoleActivity.class);
                                                startActivity(intent);
                                            }
                                        }).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                usersdRef.addListenerForSingleValueEvent(eventListener);
                if(empflag==1)
                    return;
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("users").orderByChild("phone").equalTo(mobile).addListenerForSingleValueEvent(
                            new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    if (dataSnapshot.exists()) {
                                        intentflag=1;
                                        Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                                        intent.putExtra("phone",mobile);
                                        intent.putExtra("role","HR");
                                        startActivity(intent);
                                    }
                                    else {
                                        if(empflag==0) {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            intent.putExtra("mobile", mobile);
                                            startActivity(intent);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled (DatabaseError databaseError){

                                }
                            });
                }
            }
        });
    }
    int flag=0;
    private void numberEmployee()
    {
        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if( s.length() > 1)
                    findViewById(R.id.buttonNext).setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) getSystemService(GetnumberActivity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                progressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
                mobile = editTextMobile.getText().toString().trim();
                if (mobile.isEmpty() ) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                    //
                }
                    mobile=countryCode+mobile;
                    final String userName =mobile;
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference usersdRef = rootRef.child("Employees");
                    ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.child("MyNo").getValue(String.class).equals(mobile)) {
                                if(ds.child("Password").getValue(String.class).equals(mobile)) {
                                    flag = 1;
                                    Intent intent = new Intent(getApplicationContext(), GetPasswordEmp.class);
                                    intent.putExtra("phone", mobile);
                                    startActivity(intent);
                                }
                                else
                                {
                                    flag = 1;
                                    Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                                    intent.putExtra("phone", mobile);
                                    intent.putExtra("role", "employee");
                                    startActivity(intent);
                                }

                            }
                        }
                        if(flag==0) {
                            new AlertDialog.Builder(GetnumberActivity.this).setIcon(android.R.drawable.stat_sys_warning)
                                    .setTitle("Error!")
                                    .setMessage("The number you entered is not registered with any of the company.Check your number again or contact your company with login credentials.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            editTextMobile.setText("");
                                            findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            numberEmployee();
                                        }
                                    })
                                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                usersdRef.addListenerForSingleValueEvent(eventListener);
                }
            });
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getnumber);
        String person=getIntent().getStringExtra("login");
        //Toast.makeText(this, person, Toast.LENGTH_SHORT).show();
        editTextMobile = findViewById(R.id.editTextPhone);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
        countryCodePicker = findViewById(R.id.ccp);
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                //Alert.showMessage(RegistrationActivity.this, ccp.getSelectedCountryCodeWithPlus());
                countryCode = countryCodePicker.getSelectedCountryCode();
                Toast.makeText(GetnumberActivity.this, countryCode, Toast.LENGTH_SHORT).show();
            }
        });
        if(person.equals("HR"))
            numberHR();
        else
            numberEmployee();

    }

}
