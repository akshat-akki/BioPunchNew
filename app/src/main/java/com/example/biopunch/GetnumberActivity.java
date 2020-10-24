package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GetnumberActivity extends AppCompatActivity {
    private EditText editTextMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getnumber);
        editTextMobile = findViewById(R.id.editTextPhone);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                mobile="+91"+mobile;
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }
    }
