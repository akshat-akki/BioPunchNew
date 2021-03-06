package com.example.biopunch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class AddEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private  String hr[];
    private  String min[];
    private  ArrayAdapter arrayAdapterInHr;
    private  ArrayAdapter arrayAdapterInMin;
    private  ArrayAdapter arrayAdapterOutHr;
    private  ArrayAdapter arrayAdapterOutMin;
    private  Spinner inHrSpinner;
    private  Spinner inMinSpinner;
    private  Spinner outHrSpinner;
    private  Spinner outMinSpinner;
    private  EditText EmployeeNameEditText;
    private  EditText EmployeePhoneEditText;
    String HrNo;
    private Button addbutton;
    boolean save=false;
    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        super.grantUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        addbutton=findViewById(R.id.NextActivityButtonAdd);
        addbutton.setVisibility(View.INVISIBLE);
        //initialising the arrays
        hr = new String[24];
        min = new String[60];
        for (int i = 0; i < 60; i++) {
            String a = String.valueOf(i);
            if (i < 10)
                a = "0" + a;
            if (i < 24)
                hr[i] = a;
            min[i] = a;
        }
        //editText
        EmployeeNameEditText=findViewById(R.id.EmployeeNameEditText);
        EmployeePhoneEditText=findViewById(R.id.EmployeePhoneEditText);
        //in: hour
        inHrSpinner = findViewById(R.id.inHrSpinner);
        inHrSpinner.setOnItemSelectedListener(this);
        arrayAdapterInHr = new ArrayAdapter(AddEmployee.this, android.R.layout.simple_spinner_item, hr);
        arrayAdapterInHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inHrSpinner.setAdapter(arrayAdapterInHr);
        //in: min
        inMinSpinner = findViewById(R.id.inMinSpinner);
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
        outMinSpinner = findViewById(R.id.outMinSpinner);
        outMinSpinner.setOnItemSelectedListener(this);
        arrayAdapterOutMin = new ArrayAdapter(AddEmployee.this, android.R.layout.simple_spinner_item, min);
        arrayAdapterOutMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outMinSpinner.setAdapter(arrayAdapterOutMin);
        String a=EmployeeNameEditText.getText().toString();
        String b=EmployeePhoneEditText.getText().toString();
        if(a.length()>0&&b.length()>0)
            save=true;
        if(save==true)
            addbutton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), "woohoo!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addEmployeeClicked(View view) {
        Intent i=getIntent();
        HrNo=i.getStringExtra("phone");
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(HrNo)
                .child("Employee")
                .child(EmployeePhoneEditText.getText().toString())
                .child("Name")
                .setValue(EmployeeNameEditText.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(HrNo)
                .child("Employee")
                .child(EmployeePhoneEditText.getText().toString())
                .child("Phone")
                .setValue(EmployeePhoneEditText.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Employees")
                .child(EmployeePhoneEditText.getText().toString())
                .child("HrNo")
                .setValue(HrNo);
        FirebaseDatabase.getInstance().getReference().child("Employees")
                .child(EmployeePhoneEditText.getText().toString())
                .child("MyNo")
                .setValue(EmployeePhoneEditText.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Employees")
                .child(EmployeePhoneEditText.getText().toString())
                .child("Password")
                .setValue(EmployeePhoneEditText.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Employees")
                .child(EmployeePhoneEditText.getText().toString())
                .child("Attendance");

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(HrNo)
                .child("Employee")
                .child(EmployeePhoneEditText.getText().toString())
                .child("Punched")
                .setValue("NO");

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(HrNo)
                .child("Employee")
                .child(EmployeePhoneEditText.getText().toString())
                .child("WorkTimeIn")
                .setValue(inHrSpinner.getSelectedItem().toString()+":"+inMinSpinner.getSelectedItem().toString());
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(HrNo)
                .child("Employee")
                .child(EmployeePhoneEditText.getText().toString())
                .child("WorkTimeOut")
                .setValue(outHrSpinner.getSelectedItem().toString()+":"+outMinSpinner.getSelectedItem().toString());
       // Toast.makeText(getApplicationContext(),"Employee Added Successful",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(), DashBoardHR.class);
            intent1.putExtra("phoneNumber", HrNo);
            startActivity(intent1);
    }

    public void chooseFromContacts(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                       // Toast.makeText(this, "Contact added:"+name, Toast.LENGTH_SHORT).show();
                        EmployeeNameEditText.setText(name);
                        if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                        {
                            // Query phone here. Covered next
                            String ContctMobVar = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if(ContctMobVar.substring(0,1).equals("0")==true)
                            {
                                ContctMobVar="+91"+ContctMobVar.substring(1);
                            }
                            if(ContctMobVar.substring(0,1).equals("+")==false)
                            {
                                ContctMobVar="+91"+ContctMobVar;
                            }
                            Log.i("Number", ContctMobVar);
                            String no="";
                            for(int i=0;i<ContctMobVar.length();i++){
                                if(Character.isDigit(ContctMobVar.charAt(i))==true)
                                    no=no+ContctMobVar.charAt(i);
                                else if(ContctMobVar.charAt(i)=='+')
                                    no=no+ContctMobVar.charAt(i);
                            }
                            EmployeePhoneEditText.setText(no);
                            addbutton.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
        }
    }
}
