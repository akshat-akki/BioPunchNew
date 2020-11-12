package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GetPasswordEmp extends AppCompatActivity {
    private EditText getPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password_emp);
        Intent intent=getIntent();
        String number=intent.getStringExtra("phone");
        getPassword=(EditText)findViewById(R.id.editTextGetPassword);
        getPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if(s.length()<6)
                 findViewById(R.id.WarningTextView).setVisibility(View.VISIBLE);
             else
             {
                 findViewById(R.id.WarningTextView).setVisibility(View.INVISIBLE);
                 findViewById(R.id.SetPasswordButton).setVisibility(View.VISIBLE);
                 findViewById(R.id.SetPasswordButton).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         //goto next activity
                     }
                 });
                 //save the password in firebase
             }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}