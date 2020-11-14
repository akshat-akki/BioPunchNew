package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrganizationDetailActivity extends AppCompatActivity {
String phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_detail);
        Intent i=getIntent();
        phn=i.getStringExtra("phoneNumber");
        final String role=i.getStringExtra("role");
        if(role.equals("HR")) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersdRef = rootRef.child("users");
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("phone").getValue(String.class).equals(phn)) {
                            TextView companyName=findViewById(R.id.companyTextView);
                            TextView nameHR=findViewById(R.id.hrNameText);
                            TextView emailCompany=findViewById(R.id.emailText);
                            TextView contactNo= findViewById(R.id.contactNoText);
                            if(ds.child("CompanyName").exists())
                            companyName.setText(ds.child("CompanyName").getValue(String.class));
                            if(ds.child("ContactPerson").exists())
                            nameHR.setText(ds.child("ContactPerson").getValue(String.class));
                            if(ds.child("Email").exists())
                                emailCompany.setText(" "+ds.child("Email").getValue(String.class));
                            contactNo.setText(phn);
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            usersdRef.addListenerForSingleValueEvent(eventListener);
        }
        else
        {
            //employee
          final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersdRef = rootRef.child("Employees");
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("MyNo").getValue(String.class).equals(phn)) {
                             final String HRNo;
                            if(ds.child("HrNo").exists())
                            { HRNo=ds.child("HrNo").getValue(String.class);
                            if(HRNo.length()>0)
                            {
                                TextView contactNo= findViewById(R.id.contactNoText);
                                contactNo.setText(ds.child("HrNo").getValue(String.class));
                                DatabaseReference usersdRef2 = rootRef.child("users");
                                ValueEventListener eventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            TextView companyName=findViewById(R.id.companyTextView);
                                            TextView nameHR=findViewById(R.id.hrNameText);
                                            TextView emailCompany=findViewById(R.id.emailText);
                                            if (ds.child("phone").getValue(String.class).equals(HRNo)) {
                                                if(ds.child("CompanyName").exists())
                                                    companyName.setText(ds.child("CompanyName").getValue(String.class));
                                                if(ds.child("ContactPerson").exists())
                                                    nameHR.setText(ds.child("ContactPerson").getValue(String.class));
                                                if(ds.child("Email").exists())
                                                    emailCompany.setText(" "+ds.child("Email").getValue(String.class));
                                                break;
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                };
                                usersdRef2.addListenerForSingleValueEvent(eventListener);
                            }}
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            usersdRef.addListenerForSingleValueEvent(eventListener);
        }
        }
    }