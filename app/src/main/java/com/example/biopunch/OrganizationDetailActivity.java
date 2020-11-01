package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrganizationDetailActivity extends AppCompatActivity {
String phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_detail);
        Intent i=getIntent();
        phn=i.getStringExtra("phoneNumber");
        //Toast.makeText(this, phn, Toast.LENGTH_SHORT).show();
        TextView contactNo= findViewById(R.id.contactNoText);
        contactNo.setText(phn);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
       //add name of the company
        TextView companyName=findViewById(R.id.companyTextView);
        companyName.setText("");
        //add name of hr
        TextView nameHR=findViewById(R.id.hrNameText);
        nameHR.setText("");
        //add email address from firebase
        TextView emailCompany=findViewById(R.id.companyTextView);
        companyName.setText("");
    }
}