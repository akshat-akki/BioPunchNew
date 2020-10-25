package com.example.biopunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PasswordActivity extends AppCompatActivity {

    private EditText editTextEnterPassword;
    Button Loginbutton;
    private String phone1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editTextEnterPassword=findViewById(R.id.editTextEnterPassword);
        Loginbutton=findViewById(R.id.LoginButton);
        Intent intent=getIntent();
        phone1=intent.getStringExtra("phone");
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").orderByChild("Password").equalTo(editTextEnterPassword.getText().toString()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Intent intent = new Intent(getApplicationContext(),DashBoardHR.class);

                                    startActivity(intent);
                                    // User Exists
                                    // Do your stuff here if user already exist
                                }
                                 else {
                                    Toast.makeText(getApplicationContext(), "Password entered is wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }


                        });
            };
        });
    }
}

