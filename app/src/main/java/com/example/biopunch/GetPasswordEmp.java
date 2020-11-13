package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class GetPasswordEmp extends AppCompatActivity {
    private EditText getPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password_emp);
        Intent intent=getIntent();
        final String number=intent.getStringExtra("phone");
        getPassword=(EditText)findViewById(R.id.editTextGetPasswordEmp);
        getPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if(s.length()<6)
                 findViewById(R.id.WarningTextViewEmp).setVisibility(View.VISIBLE);
             else
             {
                 findViewById(R.id.WarningTextViewEmp).setVisibility(View.INVISIBLE);
                 findViewById(R.id.SetPasswordButtonEmp).setVisibility(View.VISIBLE);
                 findViewById(R.id.SetPasswordButtonEmp).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         FirebaseDatabase.getInstance().getReference().child("Employees")
                                 .child(number)
                                 .child("Password")
                                 .setValue(getPassword.getText().toString());
                         Intent i=new Intent(getApplicationContext(),EmpDashboard.class);
                         i.putExtra("phone",number);
                         startActivity(i);

                     }
                 });

             }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}