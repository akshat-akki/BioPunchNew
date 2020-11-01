package com.example.biopunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeList extends AppCompatActivity {
    ListView listView;
    String number;
    String employeecount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        number=getIntent().getStringExtra("phoneNumber");
       // Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
        final ArrayList<String> EmployeeNames = new ArrayList<String>();
        final ArrayAdapter<String> adapter;
        listView = (ListView) findViewById(R.id.EmployeeListView);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("users").child(number).child("Employee");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long countNotpunched=0;
                long totalemployee=dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("NameHR").exists()) {
                        String nameHr = ds.child("NameHR").getValue(String.class);
                        EmployeeNames.add(0, nameHr + "(HR)");
                    }
                    if (ds.child("Name").exists()) {
                        String name = ds.child("Name").getValue(String.class);
                        EmployeeNames.add(name);
                    }
                    if (ds.child("Punched").getValue(String.class).equals("NO")) {
                        countNotpunched++;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(EmployeeList.this, android.R.layout.simple_list_item_1,EmployeeNames);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
        findViewById(R.id.addEmployeeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AddEmployee.class);
                i.putExtra("phoneNumber",number);
                i.putExtra("EmployeeList",true);
                startActivity(i);
            }
        });
    }
}