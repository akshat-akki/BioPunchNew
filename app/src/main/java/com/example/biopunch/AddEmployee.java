package com.example.biopunch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    String hr[];
    String min[];
    ArrayAdapter arrayAdapterInHr;
    ArrayAdapter arrayAdapterInMin;
    ArrayAdapter arrayAdapterOutHr;
    ArrayAdapter arrayAdapterOutMin;
    Spinner inHrSpinner;
    Spinner inMinSpinner;
    Spinner outHrSpinner;
    Spinner outMinSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        //initialising the arrays
        hr=new String[24];
        min=new String[60];
        for(int i=0;i<60;i++)
        {
            String a=String.valueOf(i);
           if(i<10)
               a="0"+a;
          if(i<24)
               hr[i]=a;
           min[i]=a;
        }
        //in: hour
        inHrSpinner = findViewById(R.id.inHrSpinner);
        inHrSpinner.setOnItemSelectedListener(this);
        arrayAdapterInHr = new ArrayAdapter(AddEmployee.this, android.R.layout.simple_spinner_item, hr);
        arrayAdapterInHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inHrSpinner.setAdapter(arrayAdapterInHr);
        //in: min
        inMinSpinner=findViewById(R.id.inMinSpinner);
        inMinSpinner.setOnItemSelectedListener(this);
        arrayAdapterInMin = new ArrayAdapter(AddEmployee.this, android.R.layout.simple_spinner_item, min);
        arrayAdapterInMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inMinSpinner.setAdapter(arrayAdapterInMin);
        //out: hour
        outHrSpinner = findViewById(R.id.outHrSpinner);
        outHrSpinner.setOnItemSelectedListener(this);
        arrayAdapterOutHr = new ArrayAdapter(AddEmployee.this, android.R.layout.simple_spinner_item, hr);
        arrayAdapterOutHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outHrSpinner.setAdapter(arrayAdapterOutHr);
        //out: min
        outMinSpinner=findViewById(R.id.outMinSpinner);
        outMinSpinner.setOnItemSelectedListener(this);
        arrayAdapterOutMin = new ArrayAdapter(AddEmployee.this, android.R.layout.simple_spinner_item, min);
        arrayAdapterOutMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outMinSpinner.setAdapter(arrayAdapterOutMin);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), "woohoo!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void addEmployeeClicked(View view)
    {
        //save all this info and add this employee to the list view

    }
}