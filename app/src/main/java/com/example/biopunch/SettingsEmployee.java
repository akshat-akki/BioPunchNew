package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsEmployee extends AppCompatActivity {
 private String phnEmp="";
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