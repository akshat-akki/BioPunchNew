package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    private String role;
    EditText change;
    EditText newP;
    EditText confirm;
    String mobileNo;
    boolean corr=true;
    boolean corrN=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent i=getIntent();
        mobileNo=i.getStringExtra("phoneNumber");
        role=i.getStringExtra("role");
        findViewById(R.id.changePasswordButton).setVisibility(View.INVISIBLE);
       change=findViewById(R.id.currentPassword);
       newP=findViewById(R.id.newPassword);
       confirm=findViewById(R.id.confirmPassword);
       change.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }
           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(role.equals("HR"))
               {
                   FirebaseDatabase.getInstance().getReference().child("users").orderByChild("Password").equalTo(change.getText().toString(),mobileNo).addListenerForSingleValueEvent(
                           new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   if (dataSnapshot.exists()) {
                                       findViewById(R.id.wrongPassword).setVisibility(View.INVISIBLE);
                                       corr=true;
                                       // User Exists
                                       // Do your stuff here if user already exist
                                   }
                                   else {
                                       corr=false;
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }


                           });
               }
               else
               {
                   FirebaseDatabase.getInstance().getReference().child("Employees").orderByChild("Password").equalTo(change.getText().toString(),mobileNo).addListenerForSingleValueEvent(
                           new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   if (dataSnapshot.exists()) {
                                       findViewById(R.id.wrongPassword).setVisibility(View.INVISIBLE);
                                       corr=true;
                                       // User Exists
                                       // Do your stuff here if user already exist
                                   }
                                   else {
                                       corr=false;
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }


                           });
               }
           }

           @Override
           public void afterTextChanged(Editable s) {
           }
       });
       newP.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                   if(corr==false)
                   {
                       findViewById(R.id.wrongPassword).setVisibility(View.VISIBLE);
                       change.setText("");
                   }
                   else
                   {
                       if(s.length()<6)
                       {
                           corrN=false;
                           findViewById(R.id.passwordLengthWrong).setVisibility(View.VISIBLE);
                       }
                       else
                       {
                           corrN=true;
                           findViewById(R.id.passwordLengthWrong).setVisibility(View.INVISIBLE);
                   }
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
       confirm.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                   if(corr==true&&corrN==true)
                   {
                       if(confirm.getText().toString().equals(newP.getText().toString()))
                       {findViewById(R.id.changePasswordButton).setVisibility(View.VISIBLE);
                           findViewById(R.id.confirmPasswordWrong).setVisibility(View.INVISIBLE);
                           findViewById(R.id.changePasswordButton).setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   if(role.equals("HR"))
                                   {
                                       FirebaseDatabase.getInstance().getReference().child("users")
                                               .child(mobileNo)
                                               .child("Password")
                                               .setValue(newP.getText().toString());
                                       Toast.makeText(ChangePassword.this, "Your password has been changed!!" , Toast.LENGTH_SHORT).show();
                                       Intent i=new Intent(getApplicationContext(),DashBoardHR.class);
                                       i.putExtra("phoneNumber",mobileNo);
                                       startActivity(i);
                                   }
                                   else
                                   {
                                       FirebaseDatabase.getInstance().getReference().child("Employees")
                                               .child(mobileNo)
                                               .child("Password")
                                               .setValue(newP.getText().toString());
                                       Toast.makeText(ChangePassword.this, "Your password has been changed!!" , Toast.LENGTH_SHORT).show();
                                       Intent i=new Intent(getApplicationContext(),EmpDashboard.class);
                                       i.putExtra("phone",mobileNo);
                                       startActivity(i);
                                   }
                               }
                           });}
                       else
                       {
                           findViewById(R.id.confirmPasswordWrong).setVisibility(View.VISIBLE);
                       }
                   }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
    }

}