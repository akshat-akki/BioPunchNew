package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetnumberActivity extends AppCompatActivity {
    String mobile;
    private EditText editTextMobile;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getnumber);
        editTextMobile = findViewById(R.id.editTextPhone);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
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
                                    Intent intent = new Intent(getApplicationContext(), DashBoardHR.class);
                                    startActivity(intent);
                                    // User Exists
                                    // Do your stuff here if user already exist
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.putExtra("mobile", mobile);
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
    }
