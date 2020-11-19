package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsEmployee extends AppCompatActivity {
 private String phnEmp="";
    private String hr;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),EmpDashboard.class);
        intent.putExtra("phone",phnEmp);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_employee);
        phnEmp=getIntent().getStringExtra("phoneNumber");
        findViewById(R.id.OrganisationDetailAccessEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OrganizationDetailActivity.class);
                intent.putExtra("phoneNumber",phnEmp);
                intent.putExtra("role","employee");
                startActivity(intent);
            }
        });
        findViewById(R.id.EmployeeDetailAccessEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersdRef = rootRef.child("Employees");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.child("MyNo").getValue(String.class).equals(phnEmp)) {
                                hr=ds.child("HrNo").getValue(String.class);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                usersdRef.addListenerForSingleValueEvent(eventListener);
                Intent intent=new Intent(getApplicationContext(),EmployeeDetailActivity.class);
                intent.putExtra("phoneNumber",phnEmp+" "+hr);
                intent.putExtra("role","employee");
                startActivity(intent);
            }
        });
        findViewById(R.id.changePasswordAccessEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangePassword.class);
                intent.putExtra("phoneNumber",phnEmp);
                intent.putExtra("role","employee");
                startActivity(intent);
            }
        });
        findViewById(R.id.logoutAccessEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),test.class);
                startActivity(intent);
            }
        });
    }
}