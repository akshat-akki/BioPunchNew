package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EmpDashboard extends AppCompatActivity {
    String empno="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_employee,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.settingsEmployee:
            {
                Intent intSet=new Intent(getApplicationContext(),SettingsEmployee.class);
                intSet.putExtra("phoneNumber",empno);
                startActivity(intSet);
                return true;}
            case R.id.downloadReportEmployee:
            {
                //return the json to excel sheet
                return(true);
            }
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_dashboard);
        Intent i=getIntent();
        empno=i.getStringExtra("phone");
    }
}