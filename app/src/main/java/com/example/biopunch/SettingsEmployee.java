package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_employee);
        findViewById(R.id.OrganisationDetailAccessEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OrganizationDetailActivity.class);
                //intent.putExtra();->add phone number of hr of this employee
                startActivity(intent);
            }
        });
        findViewById(R.id.changePasswordAccessEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangePassword.class);
                //intent.putExtra();->add phone number of hr of this employee
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