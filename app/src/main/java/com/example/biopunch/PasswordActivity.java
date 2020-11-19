package com.example.biopunch;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PasswordActivity extends AppCompatActivity {
    private EditText editTextEnterPassword;
    Button Loginbutton;
    public String phone1;
    private String role;
    private Button forgotpassword;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editTextEnterPassword = findViewById(R.id.editTextGetPassword);
        Loginbutton = findViewById(R.id.SetPasswordButton);
        Intent intent = getIntent();
        phone1 = intent.getStringExtra("phone");
        role = intent.getStringExtra("role");
        forgotpassword = findViewById(R.id.ForgotPasswordButton);
        forgotpassword.setPaintFlags(forgotpassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("mobile",phone1);
                if(role.equals("HR"))
                    intent.putExtra("roleHr",true);
                else
                    intent.putExtra("roleHr",false);
                intent.putExtra("activityPassword",true);
                startActivity(intent);
            }
        });
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals("HR"))
                {
                FirebaseDatabase.getInstance().getReference().child("users").orderByChild("Password").equalTo(editTextEnterPassword.getText().toString(),phone1).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Intent intent1 = new Intent(getApplicationContext(),DashBoardHR.class);
                                    intent1.putExtra("phoneNumber",phone1);
                                    startActivity(intent1);
                                    // User Exists
                                    // Do your stuff here if user already exist
                                }
                                 else {
                                     editTextEnterPassword.setText("");
                                    Toast.makeText(getApplicationContext(), "Password entered is wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Employees").orderByChild("Password").equalTo(editTextEnterPassword.getText().toString(),phone1).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Intent intent1 = new Intent(getApplicationContext(),EmpDashboard.class);
                                        intent1.putExtra("phone",phone1);
                                        startActivity(intent1);
                                        // User Exists
                                    }
                                    else {
                                        editTextEnterPassword.setText("");
                                       Toast.makeText(getApplicationContext(), "Password entered is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }


                            });
                    }
                };
            });
        }
    }

