package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Settings_HR extends AppCompatActivity {

String phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__h_r);
        phn=getIntent().getStringExtra("phoneNumber");
        findViewById(R.id.EmployeeAccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),EmployeeList.class);
                i.putExtra("phoneNumber",phn);
                startActivity(i);
            }
        });
       // findViewById(R.id.workTimeAccess).setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   Intent i=new Intent(getApplicationContext(),WorkTiming.class);
               // startActivity(i);
            //}
        //});
        findViewById(R.id.OrganisationDetailAccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),OrganizationDetailActivity.class);
                i.putExtra("phoneNumber",phn);
                startActivity(i);
            }
        });
        findViewById(R.id.changePasswordAccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ChangePassword.class);
                i.putExtra("phoneNumber",phn);
                startActivity(i);
            }
        });
        findViewById(R.id.logoutAccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),test.class);
                 startActivity(i);
            }
        });
    }
}